package ru.okei.med.feature.battle_screen.model

import ru.okei.med.domain.model.QuestionBody

sealed class BattleState {
    class ModuleSelection(val modules: List<String>): BattleState()
    object FindingEnemy : BattleState()
    class Loading(val load: Load= Load.Simple): BattleState(){
        enum class Load{
            Simple, EnemyConnection,
        }
    }
    data class QuestionForm(
        val question: QuestionBody,
        val userAnswer: QuestionBody.AnswerOption?,
        val numberOfQuestions:Int,
        val currentQuestion:Int
    ):BattleState()
}