package ru.okei.med.data.repos

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import ru.okei.med.domain.repos.BattleRepository

class BattleRepositoryImpl(
    private val client: HttpClient
): BattleRepository {

    override suspend fun getModules(): List<String> {
        return client.get("/battle/modules").body()
    }

}