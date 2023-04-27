package ru.okei.med.feature.battle_screen.model

sealed class BattleSideEffect {
    class ShowMessage(val message: String) : BattleSideEffect()
}