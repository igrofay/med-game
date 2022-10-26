package ru.okei.med.data.repos

import android.util.Log
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import ru.okei.med.domain.model.ProfileBody
import ru.okei.med.domain.repos.ProfileRepository

class ProfileRepositoryImpl(
    private val client: HttpClient
): ProfileRepository {
    override suspend fun getProfile(token: String): ProfileBody {
        return client.get("/profile"){
            header(HttpHeaders.Authorization, token)
        }.body()
    }

}