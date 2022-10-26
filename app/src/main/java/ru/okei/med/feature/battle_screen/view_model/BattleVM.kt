package ru.okei.med.feature.battle_screen.view_model

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import ru.okei.med.domain.model.Errors
import ru.okei.med.domain.model.QuestionBody
import ru.okei.med.domain.model.TypeBattle.*
import ru.okei.med.domain.repos.BattleRepository
import ru.okei.med.feature.base.EventBase
import ru.okei.med.feature.battle_screen.model.BattleEvent
import ru.okei.med.feature.battle_screen.model.BattleState
import javax.inject.Inject

@HiltViewModel
class BattleVM @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val battleRepository: BattleRepository
) : ViewModel(), EventBase<BattleEvent> {
    private val _state = mutableStateOf<BattleState>(BattleState.Loading())
    val state: State<BattleState> get() = _state

    init {
        _state.value = BattleState.QuestionForm(
            QuestionBody(
                QuestionBody.Type.Image,
                "Какая мышца в теле человека является самой крупной?",
                "Какая мышца в теле человека является самой крупной?",
                "https://medexpert-vl.ru/wp-content/uploads/rentgen-scaled-1.jpg",
                100,
                QuestionBody.AnswerOption(QuestionBody.Type.Text,"Плечевой",null),
                listOf(
                    QuestionBody.AnswerOption(QuestionBody.Type.Text,"Плечевой",null),
                    QuestionBody.AnswerOption(QuestionBody.Type.Text,"Спиной",null),
                    QuestionBody.AnswerOption(QuestionBody.Type.Text,"Черепной",null),
                    QuestionBody.AnswerOption(QuestionBody.Type.Text,"Кистевой",null)
                )
            ), null,
            15,
            5
        )
//        try {
//            val strTypeBattle = savedStateHandle.get<String>("typeBattle")!!
//            when(valueOf(strTypeBattle)){
//                Simpler -> {
//                    viewModelScope.launch {
//                        val modules = battleRepository.getModules()
//                        _state.value = BattleState.ModuleSelection(modules)
//                    }
//                }
//                Rating -> {
//                    _state.value = BattleState.FindingEnemy
//                }
//            }
//        }catch (e:Throwable){errorProcessing(e)}
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
                _state.value = (_state.value as BattleState.QuestionForm).copy(userAnswer = event.answerOption)
            }
            BattleEvent.CancelSearch -> {

            }
        }
    }

}