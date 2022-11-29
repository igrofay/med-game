package ru.okei.med.feature.friends.model

import ru.okei.med.domain.model.FriendInfo


sealed class FriendsState {
    object Loading:FriendsState()
    object BadInternetConnection: FriendsState()
    class FriendList(
        val friends: Map<FriendInfo.FriendStatus, List<FriendInfo>>
    ): FriendsState()
    class FoundUserList(val userList: List<FriendInfo>):FriendsState()
}