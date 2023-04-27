package ru.okei.med.domain.repos

import io.ktor.client.plugins.websocket.*
import ru.okei.med.domain.model.RequestForFight

interface BattleRepository {

    suspend fun getRequestForFight(tokenAccess:String) : RequestForFight?

    suspend fun refuseRequest(tokenAccess:String,tokenRoom: String)
    suspend fun getModules(department:String): List<String>
    suspend fun searchForEnemyModule(tokenAccess:String,
                                     department:String,
                                     module:String,
                                     countPlayers: Int,
                                     connection: suspend DefaultClientWebSocketSession.() -> Unit)
    suspend fun searchForEnemyRating(tokenAccess:String,
                                     department:String,
                                     connection: suspend DefaultClientWebSocketSession.() -> Unit)
    suspend fun searchForFriendModule(
        tokenAccess: String,
        emailFriend: String,
        department:String,
        module:String,
        connection: suspend DefaultClientWebSocketSession.() -> Unit
    )
    suspend fun connectionRoom(tokenRoom:String,
                               tokenAccess:String,
                               connection: suspend DefaultClientWebSocketSession.() -> Unit)


}