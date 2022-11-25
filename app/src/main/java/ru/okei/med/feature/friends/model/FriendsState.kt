package ru.okei.med.feature.friends.model

import ru.okei.med.domain.model.FriendInfo


sealed class FriendsState {
    object Loading:FriendsState()
    object BadInternetConnection: FriendsState()
    class Success(
        val friends: Map<FriendInfo.FriendState, List<FriendInfo>>
    ): FriendsState()
}