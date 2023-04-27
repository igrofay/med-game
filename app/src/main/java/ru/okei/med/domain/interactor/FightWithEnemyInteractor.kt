package ru.okei.med.domain.interactor

import android.util.Log
import io.ktor.client.plugins.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.*
import ru.okei.med.domain.interactor.TypeJson.*
import ru.okei.med.domain.model.*
import ru.okei.med.domain.repos.BattleRepository
import ru.okei.med.domain.repos.TokenRepository
import ru.okei.med.utils.readObject

typealias EnemyFound = ()->Unit
typealias NewQuestionCameUp = (QuestionBody)->Unit
typealias NewRatingTable = (StateGame)-> Unit
typealias FightCanceled = ()-> Unit

private enum class TypeJson{
    StateGameBodyJson,
    QuestionBodyJson
}

class FightWithEnemyInteractor(
    private val tokenRepository: TokenRepository,
    private val battleRepository: BattleRepository
) {
    private val scope = CoroutineScope(Dispatchers.IO + CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e("FightWithEnemyInteractor::$coroutineContext",throwable.message.toString())
    })
    private var socketSession: DefaultClientWebSocketSession? = null
    private lateinit var enemyFound: EnemyFound
    private lateinit var newRatingTable: NewRatingTable
    private lateinit var newQuestionCameUp: NewQuestionCameUp
    private lateinit var typeBattle: TypeBattle
    private lateinit var department:String
    private lateinit var fightCanceled : FightCanceled
    @Volatile
    private var tokenRoom: String= ""

    fun init(
        department:String,typeBattle: TypeBattle,
        enemyFound:EnemyFound,newQuestionCameUp: NewQuestionCameUp,
        newRatingTable: NewRatingTable, fightCanceled : FightCanceled
    ){
        this.typeBattle = typeBattle
        this.department = department
        this.enemyFound = enemyFound
        this.newQuestionCameUp= newQuestionCameUp
        this.newRatingTable = newRatingTable
        this.fightCanceled = fightCanceled
    }
    fun search(module:String? = null, emailFriend: String? = null){
        scope.launch {
            when(typeBattle){
                TypeBattle.Simple -> battleRepository.searchForEnemyModule(
                    tokenAccess = tokenRepository.access,
                    department = department,
                    module = module!!,
                    countPlayers = 2,
                ){
                    connectionSearchingEnemy()
                }
                TypeBattle.Rating -> battleRepository.searchForEnemyRating(
                    tokenAccess = tokenRepository.access,
                    department = department,
                ){
                    connectionSearchingEnemy()
                }
                TypeBattle.Single -> battleRepository.searchForEnemyModule(
                    tokenAccess = tokenRepository.access,
                    department = department,
                    module = module!!,
                    countPlayers = 1
                ){
                    connectionSearchingEnemy()
                }
                TypeBattle.WithFriend -> battleRepository.searchForFriendModule(
                    tokenAccess = tokenRepository.access,
                    emailFriend = emailFriend!!,
                    department = department,
                    module = module!!,
                ){
                    connectionSearchingEnemy()
                }
            }
        }
    }

    private suspend fun DefaultClientWebSocketSession.connectionSearchingEnemy(){
        socketSession = this
        try {
            for (message in incoming) {
                message as? Frame.Text ?: continue
                tokenRoom = message.readText()
                break
            }
        }catch (e : ClientRequestException){
            when(e.response.status){
                HttpStatusCode.FailedDependency-> fightCanceled()
            }
        } catch (e: Exception) {
            Log.e("SearchForEnemyController::connectionSearchingEnemy", e.message.toString())
        }finally {
            close()
            socketSession = null
            if (tokenRoom == "429"){
                fightCanceled.invoke()
            }else if (tokenRoom.isNotBlank()){
                enemyFound.invoke()
                scope.launch { connectionRoom() }
            }
        }
    }

    fun searchRoom(tokenRoom: String){
        scope.launch {
            this@FightWithEnemyInteractor.tokenRoom = tokenRoom
            connectionRoom()
        }
    }


    private suspend fun connectionRoom(){
        battleRepository.connectionRoom(tokenRoom,tokenRepository.access){
            socketSession = this
            try {
                for (item in incoming){
                    readData(item)
                }
            }catch (e:Exception){
                Log.e("SearchForEnemyController::connectionRoom", e.message.toString())
            }
        }
    }

    private var whatTypeToSterilize = StateGameBodyJson

    private suspend fun DefaultClientWebSocketSession.readData(message:Frame){
        try {
            whatTypeToSterilize = when(whatTypeToSterilize){
                StateGameBodyJson -> {
                    val rating = readObject<StateGame>(message)
                    newRatingTable.invoke(rating)
                    QuestionBodyJson
                }
                QuestionBodyJson -> {
                    val question = readObject<QuestionBody>(message)
                    newQuestionCameUp.invoke(question)
                    StateGameBodyJson
                }
            }
        }catch (e:Throwable){
            Log.e("SearchForEnemyController::readData", e.message.toString())
        }
    }




    fun reply(answerOption: QuestionBody.AnswerOption){
        scope.launch {
            socketSession?.sendSerialized(answerOption)
        }
    }


    fun close(){
        scope.launch {
            socketSession?.close()
            socketSession = null
            scope.cancel()
        }
    }
}
