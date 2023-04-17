package ru.okei.med.feature.friends.model

sealed class FriendsEvent {
    object RetryRequest: FriendsEvent()
    object GetFriendList: FriendsEvent()
    class SearchUser(val textForSearching:String): FriendsEvent()
    class DeleteFriend(val email:String): FriendsEvent()
    class SendFriendRequest(val email: String): FriendsEvent()
    class CanselFriendRequest(val email: String): FriendsEvent()
    class AcceptFriendRequest(val email: String): FriendsEvent()
    class FightFriendRequest(val email: String) : FriendsEvent()
}