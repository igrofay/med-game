package ru.okei.med.feature.friends.model

sealed class FriendsSideEffect {
    class GoToFightWithFriend(val email:String) : FriendsSideEffect()
}