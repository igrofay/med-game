package ru.okei.med.domain.repos

import ru.okei.med.domain.model.FriendInfo

interface FriendsRepository {

    suspend fun getFriends(token:String) : List<FriendInfo>

    suspend fun getUsers(token:String,nameUser: String): List<FriendInfo>
    suspend fun deleteFriend(token:String, email:String)
    suspend fun acceptFriendRequest(token:String, email:String)
    suspend fun cancelFriendRequest(token:String, email:String)
    suspend fun sendFriendRequest(token:String, email:String)
}