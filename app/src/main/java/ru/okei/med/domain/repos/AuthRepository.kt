package ru.okei.med.domain.repos

import ru.okei.med.domain.model.TokensBody

interface AuthRepository {
    suspend fun signIn(email:String, password:String): TokensBody
    suspend fun signUp(email:String,nickname:String, password:String): TokensBody
    suspend fun restoreSession(refreshToken:String): TokensBody
}