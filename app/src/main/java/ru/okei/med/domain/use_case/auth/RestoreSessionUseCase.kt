package ru.okei.med.domain.use_case.auth

import android.util.Log
import io.ktor.client.plugins.*
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import ru.okei.med.domain.model.Errors
import ru.okei.med.domain.repos.AuthRepository
import ru.okei.med.domain.repos.TokenRepository

class RestoreSessionUseCase(
    private val tokenRepository: TokenRepository,
    private val authRepository: AuthRepository,
) {

    suspend fun execute()= runCatching<Unit> {
        val refreshToken = tokenRepository.refresh
        if(refreshToken.isEmpty()){
            throw Errors.Auth.RefreshTokenNotAvailable
        }
        try {
            val body = authRepository.restoreSession(refreshToken)
            tokenRepository.access = body.access
            tokenRepository.refresh = body.refresh
        }catch (e: ClientRequestException){
            when(e.response.status){
                BadRequest->{
                    tokenRepository.access = ""
                    tokenRepository.refresh = ""
                    throw Errors.Auth.RefreshTokenNotAvailable
                }
                else -> throw e
            }
        }
    }
}