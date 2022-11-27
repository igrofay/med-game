package ru.okei.med.domain.use_case.frends

import io.ktor.utils.io.errors.*
import ru.okei.med.domain.model.Errors
import ru.okei.med.domain.model.FriendInfo
import ru.okei.med.domain.repos.ProfileRepository


class GetFriendsUseCase(
    private val profileRepository: ProfileRepository,
){
    suspend fun execute() = runCatching<Map<FriendInfo.FriendState, List<FriendInfo>>> {
        try {
            val answer = FriendInfo.FriendState.values().associateWith { mutableListOf<FriendInfo>() }
            profileRepository.getFriends().forEach{ item ->
                answer[item.state]?.add(item)
            }
            answer
        }catch (e: IOException) {
            throw Errors.Request.NetworkError(message = null)
        }
    }
}