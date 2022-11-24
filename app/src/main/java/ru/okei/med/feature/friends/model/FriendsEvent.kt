package ru.okei.med.feature.friends.model

sealed class FriendsEvent {
    object RetryRequest: FriendsEvent()
}