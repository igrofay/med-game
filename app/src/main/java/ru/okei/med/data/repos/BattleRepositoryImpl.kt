package ru.okei.med.data.repos

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.content.*
import ru.okei.med.data.model.SettingRoomBody
import ru.okei.med.domain.model.RequestForFight
import ru.okei.med.domain.model.TypeBattle
import ru.okei.med.domain.repos.BattleRepository

class BattleRepositoryImpl(
    private val client: HttpClient
): BattleRepository {
    override suspend fun getRequestForFight(tokenAccess: String): RequestForFight? {
        val answer =  client.get("/room/requests"){
            header(HttpHeaders.Authorization, tokenAccess)
        }
        return when(answer.status){
            HttpStatusCode.NoContent -> null
            HttpStatusCode.OK -> answer.body()
            else -> null
        }
    }

    override suspend fun refuseRequest(tokenAccess: String, tokenRoom: String) {
        client.delete("/room/${tokenRoom}"){
            header(HttpHeaders.Authorization, tokenAccess)
        }
    }


    override suspend fun getModules(department:String): List<String> {
        return client.get("/module/${department}").body()
    }

    override suspend fun searchForEnemyModule(
        tokenAccess:String,
        department:String,
        module:String,
        countPlayers: Int,
        connection: suspend DefaultClientWebSocketSession.() -> Unit
    ) {
        client.webSocket(
            method = HttpMethod.Get,
            path = "/main",
            request = {
                header(HttpHeaders.Authorization, tokenAccess)
                url.protocol = URLProtocol.WS
            },
            block = {
                sendSerialized(SettingRoomBody(
                    nameModule = module,
                    nameDepartment = department,
                    type = TypeBattle.Simple,
                    countPlayers = countPlayers
                ))
                connection()
            }
        )
    }

    override suspend fun searchForEnemyRating(
        tokenAccess:String,
        department: String,
        connection: suspend DefaultClientWebSocketSession.() -> Unit
    ) {
        client.webSocket(
            method = HttpMethod.Get,
            path = "/main",
            request = {
                header(HttpHeaders.Authorization, tokenAccess)
                url.protocol = URLProtocol.WS
            },
            block = {
                sendSerialized(SettingRoomBody(
                    nameModule = null,
                    nameDepartment = department,
                    type = TypeBattle.Rating
                ))
                connection()
            }
        )
    }

    override suspend fun searchForFriendModule(
        tokenAccess: String,
        emailFriend: String,
        department: String,
        module: String,
        connection: suspend DefaultClientWebSocketSession.() -> Unit
    ) {
        client.webSocket(
            method = HttpMethod.Get,
            path = "/main/$emailFriend",
            request = {
                header(HttpHeaders.Authorization, tokenAccess)
                url.protocol = URLProtocol.WS
            },
            block = {
                sendSerialized(SettingRoomBody(
                    nameModule = module,
                    nameDepartment = department,
                    type = TypeBattle.Simple,
                ))
                connection()
            }
        )
    }

    override suspend fun connectionRoom(
        tokenRoom: String,
        tokenAccess: String,
        connection: suspend DefaultClientWebSocketSession.() -> Unit
    ) {
        client.webSocket(
            method = HttpMethod.Get,
            path = "/room/${tokenRoom}",
            request = {
                header(HttpHeaders.Authorization, tokenAccess)
                url.protocol = URLProtocol.WS
            },
            block = connection
        )
    }

}