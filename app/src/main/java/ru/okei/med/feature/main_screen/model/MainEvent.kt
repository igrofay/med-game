package ru.okei.med.feature.main_screen.model


sealed class MainEvent {
    object UpdateData : MainEvent()

    object ClearSideEffect: MainEvent()

    object RefuseToFight : MainEvent()

    object AcceptFight : MainEvent()
}