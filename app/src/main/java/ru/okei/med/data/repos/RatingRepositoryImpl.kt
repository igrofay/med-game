package ru.okei.med.data.repos

import io.ktor.client.*
import ru.okei.med.domain.model.RatingInfo
import ru.okei.med.domain.repos.RatingRepository

class RatingRepositoryImpl(
    private val client: HttpClient
): RatingRepository {
    override suspend fun getRatingInfo(token: String): RatingInfo {
        return RatingInfo(
            listPlayers = listOf(
                RatingInfo.Player("wd@da.ad","wdawdawdad dawdaw21dl"),
                RatingInfo.Player("wd@da.ad","2e2q dawdaw21dl"),
                RatingInfo.Player()
            ),
        )
    }

}