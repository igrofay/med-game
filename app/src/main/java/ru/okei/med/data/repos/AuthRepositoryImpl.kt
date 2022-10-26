package ru.okei.med.data.repos

import android.util.Log
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import ru.okei.med.data.model.SignInBody
import ru.okei.med.data.model.SignUpBody
import ru.okei.med.domain.model.TokensBody
import ru.okei.med.domain.repos.AuthRepository

class AuthRepositoryImpl(
    private val client: HttpClient
): AuthRepository {
    override suspend fun signIn(email: String, password: String): TokensBody {
        return client.post(urlString = "/sign_in"){
            contentType(ContentType.Application.Json)
            setBody(SignInBody(email,password))
        }.body()
    }

    override suspend fun signUp(email: String, nickname: String, password: String): TokensBody {
        return client.post(urlString = "/sign_up"){
            contentType(ContentType.Application.Json)
            setBody(SignUpBody(email,nickname, password))
        }.body()
    }

    override suspend fun restoreSession(refreshToken: String): TokensBody {
        return client.post(urlString = "/token"){
            contentType(ContentType.Application.Any)
            setBody(refreshToken)
        }.body()
    }
}