package ru.okei.med.data.repos

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import ru.okei.med.domain.model.ProfileBody
import ru.okei.med.domain.repos.ProfileRepository
import java.io.InputStream

class ProfileRepositoryImpl(
    private val client: HttpClient,
): ProfileRepository {
    override suspend fun getProfile(token: String): ProfileBody {
        return client.get("/profile"){
            header(HttpHeaders.Authorization, token)
        }.body()
    }

    override suspend fun sendImageProfile(imageFile: InputStream, token:String) {
        client.post("/profileIcon"){
            header(HttpHeaders.Authorization, token)
            setBody(imageFile.readBytes())
        }
    }


}