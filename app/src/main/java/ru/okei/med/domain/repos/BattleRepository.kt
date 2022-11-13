package ru.okei.med.domain.repos

import io.ktor.client.plugins.websocket.*

interface BattleRepository {
    suspend fun getModules(department:String): List<String>
    suspend fun searchForEnemyModule(tokenAccess:String,
                                     department:String,
                                     module:String,
                                     connection: suspend DefaultClientWebSocketSession.() -> Unit)
    suspend fun searchForEnemyRating(tokenAccess:String,
                                     department:String,
                                     connection: suspend DefaultClientWebSocketSession.() -> Unit)
    suspend fun connectionRoom(tokenRoom:String,
                               tokenAccess:String,
                               connection: suspend DefaultClientWebSocketSession.() -> Unit)
}