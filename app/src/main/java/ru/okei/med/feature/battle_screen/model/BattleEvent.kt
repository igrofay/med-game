package ru.okei.med.feature.battle_screen.model

import ru.okei.med.domain.model.QuestionBody

sealed class BattleEvent {
    class Reply(val answerOption: QuestionBody.AnswerOption): BattleEvent()
    object CancelSearch : BattleEvent()
}