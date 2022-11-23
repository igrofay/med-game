package ru.okei.med.feature.battle_screen.view_model

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.okei.med.domain.interactor.FightWithEnemyInteractor
import ru.okei.med.domain.model.ProfileBody
import ru.okei.med.domain.model.QuestionBody
import ru.okei.med.domain.model.StateGame
import ru.okei.med.domain.model.TypeBattle
import ru.okei.med.domain.model.TypeBattle.Rating
import ru.okei.med.domain.model.TypeBattle.Simple
import ru.okei.med.domain.repos.BattleRepository
import ru.okei.med.domain.use_case.profile.GetProfileUseCase
import ru.okei.med.feature.base.EventBase
import ru.okei.med.feature.battle_screen.model.BattleEvent
import ru.okei.med.feature.battle_screen.model.BattleState
import javax.inject.Inject

@HiltViewModel
class BattleVM @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val battleRepository: BattleRepository,
    private val fightWithEnemy: FightWithEnemyInteractor,
    private val profileUseCase: GetProfileUseCase,
) : ViewModel(), EventBase<BattleEvent> {
    private var currentQuestion = 0
    private val typeBattle = TypeBattle.valueOf(savedStateHandle.get<String>("typeBattle")!!)
    private val _state = mutableStateOf<BattleState>(BattleState.Loading())
    val state: State<BattleState> get() = _state

    init {
        viewModelScope.launch {
            profileUseCase.execute()
                .onSuccess { profile: ProfileBody -> initThis(profile.department.name) }
                .onFailure(::errorProcessing)
        }
    }

    private fun initThis(department:String){
        fightWithEnemy.init(
            department,
            typeBattle,
            enemyFound = {
                _state.value = BattleState.Loading(BattleState.Loading.Load.EnemyConnection)
            },
            newQuestionCameUp = { question ->
                _state.value = BattleState.QuestionForm(
                    question = question,
                    userAnswer = null,
                    currentQuestion = ++currentQuestion
                )
            },
            newRatingTable = { rating ->
                _state.value = BattleState.ViewRatingGame(rating)
            }
        )
        when(typeBattle){
            Simple -> {
                viewModelScope.launch {
                    runCatching {
                        battleRepository.getModules(department)
                    }.onSuccess { modules ->
                        _state.value = BattleState.ModuleSelection(modules)
                    }.onFailure(::errorProcessing)
                }
            }
            Rating -> {
                fightWithEnemy.search()
                _state.value = BattleState.FindingEnemy
            }
        }
    }

    private fun errorProcessing(e:Throwable){
        when(e){
            else ->{
                Log.e("BattleVM",e.message.toString())
            }
        }
    }

    override fun onEvent(event: BattleEvent) {
        when(event){
            is BattleEvent.Reply -> {
                if ((_state.value as BattleState.QuestionForm).userAnswer != null) return
                fightWithEnemy.reply(event.answerOption)
                _state.value = (_state.value as BattleState.QuestionForm).copy(userAnswer = event.answerOption)
            }
            BattleEvent.Cancel -> {
                fightWithEnemy.close()
            }
            is BattleEvent.ChosenModule -> {
                fightWithEnemy.search(event.module)
                _state.value = BattleState.FindingEnemy
            }
        }
    }

    private fun test1(){
                _state.value = BattleState.QuestionForm(
            QuestionBody(
                QuestionBody.TypeQuestion.Image,
                "Какая мышца в теле человека является самой крупной?",
                "Какая мышца в теле человека является самой крупной?",
                "https://medexpert-vl.ru/wp-content/uploads/rentgen-scaled-1.jpg",
                100,
                rightAnswer = QuestionBody.AnswerOption(QuestionBody.TypeAnswer.Image,"Plech","https://medexpert-vl.ru/wp-content/uploads/rentgen-scaled-1.jpg"),
                listOf(
                    QuestionBody.AnswerOption(QuestionBody.TypeAnswer.Image,"Плечевой","https://medexpert-vl.ru/wp-content/uploads/rentgen-scaled-1.jpg"),
                    QuestionBody.AnswerOption(QuestionBody.TypeAnswer.Image,"Спиной","https://medexpert-vl.ru/wp-content/uploads/rentgen-scaled-1.jpg"),
                    QuestionBody.AnswerOption(QuestionBody.TypeAnswer.Image,"Черепной","https://medexpert-vl.ru/wp-content/uploads/rentgen-scaled-1.jpg"),
                    QuestionBody.AnswerOption(QuestionBody.TypeAnswer.Image,"Кистевой","https://medexpert-vl.ru/wp-content/uploads/rentgen-scaled-1.jpg")
                )
            ), null,
            5
        )
    }
    private fun test2(){
        _state.value = BattleState.ViewRatingGame(
            StateGame(
                isEndGame = false,
                rating = listOf(
                    StateGame.GamePointsRating("oleg",
                        "https://assets.gq.ru/photos/5d9f4654cd52870008328b53/master/w_1600,c_limit/05.jpg",
                        5,
                        10,
                        5,
                    ),
                    StateGame.GamePointsRating("Максим гудеев", "", 9, 10, 3,)
                ),
                "Максим гудеев"
            )
        )
    }

    override fun onCleared() {
        fightWithEnemy.close()
        super.onCleared()
    }


}