package ru.okei.med.feature.battle_screen.model

import ru.okei.med.domain.model.QuestionBody
import ru.okei.med.domain.model.StateGame

sealed class BattleState {
    class ModuleSelection(val modules: List<String>): BattleState()
    object FindingEnemy : BattleState()
    object WaitingFriend : BattleState()
    class Loading(val load: Load= Load.Simple): BattleState(){
        enum class Load{
            Simple, EnemyConnection,
        }
    }
    data class QuestionForm(
        val question: QuestionBody,
        val userAnswer: QuestionBody.AnswerOption?,
        val currentQuestion:Int
    ):BattleState()
    data class ViewRatingGame(val statusGame: StateGame) :BattleState()
}