package ru.okei.med.domain.use_case.frends

import io.ktor.utils.io.errors.*
import ru.okei.med.domain.model.Errors
import ru.okei.med.domain.model.FriendInfo
import ru.okei.med.domain.repos.FriendsRepository
import ru.okei.med.domain.repos.TokenRepository

class GetUserWithSuchNameUseCase(
    private val tokenRepository: TokenRepository,
    private val profileRepository: FriendsRepository,
){

    suspend fun execute(name:String) = runCatching<List<FriendInfo>>{
        try {
            val token = tokenRepository.access
            profileRepository.getUsers(token, name)
        }catch (e: IOException) {
            throw Errors.Request.NetworkError(message = null)
        }
    }
}