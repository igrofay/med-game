package ru.okei.med.domain.interactor

import android.util.Log
import io.ktor.client.plugins.websocket.*
import io.ktor.serialization.*
import io.ktor.util.reflect.*
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

private enum class TypeJson{
    StateGameBodyJson,
    QuestionBodyJson
}

class FightWithEnemyInteractor(
    private val tokenRepository: TokenRepository,
    private val battleRepository: BattleRepository
) {
    private val scope = CoroutineScope(
        Dispatchers.IO )
    private var socketSession: DefaultClientWebSocketSession? = null
    private lateinit var enemyFound: EnemyFound
    private lateinit var newRatingTable: NewRatingTable
    private lateinit var newQuestionCameUp: NewQuestionCameUp
    private lateinit var typeBattle: TypeBattle
    private lateinit var department:String
    private lateinit var tokenRoom: String

    fun init(
        department:String,typeBattle: TypeBattle,
        enemyFound:EnemyFound,newQuestionCameUp: NewQuestionCameUp,
        newRatingTable: NewRatingTable
    ){
        this.typeBattle = typeBattle
        this.department = department
        this.enemyFound = enemyFound
        this.newQuestionCameUp= newQuestionCameUp
        this.newRatingTable = newRatingTable
    }
    fun search(module:String? = null){
        scope.launch {
            when(typeBattle){
                TypeBattle.Simpler -> battleRepository.searchForEnemyModule(
                    tokenAccess = tokenRepository.access,
                    department = department,
                    module = module!!,
                ){
                    connectionSearchingEnemy()
                }
                TypeBattle.Rating -> battleRepository.searchForEnemyRating(
                    tokenAccess = tokenRepository.access,
                    department = department,
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
                println(tokenRoom)
                break
            }
            enemyFound.invoke()
            close()
            socketSession = null
            scope.launch { connectionRoom() }
        } catch (e: Exception) {
            Log.e("SearchForEnemyController/connectionSearchingEnemy", e.message.toString())
        }
    }


    private suspend fun connectionRoom(){
        battleRepository.connectionRoom(tokenRoom,tokenRepository.access){
            socketSession = this
            try {
                for (message in incoming) {
                    readData(message)
                }
            }catch (e:Exception){
                Log.e("SearchForEnemyController/connectionRoom", e.message.toString())
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
            Log.e("SearchForEnemyController/readData", e.message.toString())
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
