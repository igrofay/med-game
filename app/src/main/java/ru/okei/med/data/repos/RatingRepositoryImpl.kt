package ru.okei.med.data.repos

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import ru.okei.med.domain.model.RatingInfo
import ru.okei.med.domain.repos.RatingRepository

class RatingRepositoryImpl(
    private val client: HttpClient
): RatingRepository {
    override suspend fun getRatingInfo(token: String): RatingInfo {
        return client.get("/rating"){
            header(HttpHeaders.Authorization,token)
        }.body()
    }

}