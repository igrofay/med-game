package ru.okei.med.data.repos

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import ru.okei.med.domain.model.FriendInfo
import ru.okei.med.domain.repos.FriendsRepository

class FriendsRepositoryImpl(val httpClient: HttpClient): FriendsRepository {

    override suspend fun getFriends(token:String): List<FriendInfo> {
        return httpClient.get("/friends"){
            header(HttpHeaders.Authorization, token)
        }.body()
    }

    override suspend fun getUsers(token: String, nameUser: String): List<FriendInfo> {
        return httpClient.post("/players"){
            header(HttpHeaders.Authorization, token)
            setBody(nameUser)
        }.body()
    }

    override suspend fun deleteFriend(token: String, email: String) {
        httpClient.delete("/friend/$email"){
            header(HttpHeaders.Authorization, token)
        }
    }

    override suspend fun acceptFriendRequest(token: String, email: String) {
        httpClient.patch("/friend/$email"){
            header(HttpHeaders.Authorization, token)
        }
    }

    override suspend fun cancelFriendRequest(token: String, email: String) {
        httpClient.delete("/friendReq/$email"){
            header(HttpHeaders.Authorization, token)
        }
    }

    override suspend fun sendFriendRequest(token: String, email: String) {
        httpClient.post("/friendReq/$email"){
            header(HttpHeaders.Authorization, token)
        }
    }

}