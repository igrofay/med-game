package ru.okei.med.feature.main_screen.model

sealed class MainSideEffect {
    class GoToFightWithFriend(val tokenRoom: String) : MainSideEffect()
    class ShowMessage(val message: String) : MainSideEffect()
}