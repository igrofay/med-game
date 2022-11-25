package ru.okei.med.domain.use_case.frends

import ru.okei.med.domain.model.FriendInfo
import ru.okei.med.domain.repos.ProfileRepository


class GetFriendsUseCase(
    private val profileRepository: ProfileRepository
){
    suspend fun execute() = runCatching<Map<FriendInfo.FriendState, List<FriendInfo>>> {
        val answer = FriendInfo.FriendState.values().associateWith { mutableListOf<FriendInfo>() }
        profileRepository.getFriends().forEach{ item ->
            answer[item.state]!!.add(item)
        }
        answer
    }
}