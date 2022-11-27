package ru.okei.med.feature.friends.model

sealed class FriendsEvent {
    object RetryRequest: FriendsEvent()
    class SearchUser(val textForSearching:String): FriendsEvent()
}