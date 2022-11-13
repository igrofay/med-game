package ru.okei.med.domain.use_case.auth

import android.content.Context
import io.ktor.client.plugins.*
import io.ktor.http.HttpStatusCode.Companion.Conflict
import io.ktor.utils.io.errors.*
import ru.okei.med.R
import ru.okei.med.domain.model.Errors
import ru.okei.med.domain.repos.AuthRepository
import ru.okei.med.domain.repos.TokenRepository
import ru.okei.med.utils.CheckSpelling

class SignUpUseCase(
    private val tokenRepository: TokenRepository,
    private val authRepository: AuthRepository,
    private val appContext: Context
) {
    suspend fun execute(email:String,nickname:String, password:String) = runCatching<Unit>{
        if (!CheckSpelling.check(email, CheckSpelling.Type.Email)){
            throw Errors.Auth.ErrorInEmail(
                message = appContext.getString(R.string.wrong_mail)
            )
        }
        if (!CheckSpelling.check(password, CheckSpelling.Type.Password)){
            throw Errors.Auth.ErrorInPassword(
                message = appContext.getString(R.string.password_must_be_more_than_6_characters)
            )
        }
        if (!CheckSpelling.check(nickname, CheckSpelling.Type.NameAndSecondName)){
            throw Errors.Auth.ErrorInName(
                message = appContext.getString(R.string.enter_your_first_and_last_name)
            )
        }
        try {
            val body = authRepository.signUp(email, nickname,password)
            tokenRepository.access = body.access
            tokenRepository.refresh = body.refresh
        }catch (e: ClientRequestException){
            when(e.response.status){
                Conflict -> throw Errors.Auth.ErrorInEmail(
                    message = appContext.getString(R.string.this_mail_is_already_registered)
                )
                else-> throw e
            }
        }catch (e: IOException) {
            throw Errors.Request.NetworkError(
                message = appContext.getString(R.string.network_error)
            )
        }
    }
}