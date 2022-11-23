package ru.okei.med.feature.friends.model


sealed class FriendsState {
    object Loading:FriendsState()
    object BadInternetConnection: FriendsState()
}