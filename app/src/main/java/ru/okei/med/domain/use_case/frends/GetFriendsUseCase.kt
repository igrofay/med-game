package ru.okei.med.domain.use_case.frends

import io.ktor.utils.io.errors.*
import ru.okei.med.domain.model.Errors
import ru.okei.med.domain.model.FriendInfo
import ru.okei.med.domain.repos.FriendsRepository
import ru.okei.med.domain.repos.ProfileRepository
import ru.okei.med.domain.repos.TokenRepository


class GetFriendsUseCase(
    private val tokenRepository: TokenRepository,
    private val profileRepository: FriendsRepository,
){
    suspend fun execute() = runCatching<Map<FriendInfo.FriendStatus, List<FriendInfo>>> {
        try {
            val answer = FriendInfo.FriendStatus.values()
                .toMutableSet()
                .apply {
                    remove(FriendInfo.FriendStatus.NotFriends)
                }.associateWith { mutableListOf<FriendInfo>() }
            val token = tokenRepository.access
            profileRepository.getFriends(token).forEach{ item ->
                answer[item.status]!!.add(item)
            }
            answer
        }catch (e: IOException) {
            throw Errors.Request.NetworkError(message = null)
        }
    }
}