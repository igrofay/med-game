package ru.okei.med.domain.use_case.frends

import android.util.Log
import ru.okei.med.domain.repos.FriendsRepository
import ru.okei.med.domain.repos.TokenRepository

class ActionOnFriendUseCase(
    private val tokenRepository: TokenRepository,
    private val friendsRepository: FriendsRepository
) {
    suspend fun execute(email:String,action: Action) = runCatching {
        try {
            val token = tokenRepository.access
            when(action){
                Action.Delete -> friendsRepository.deleteFriend(token, email)
                Action.AcceptRequest -> friendsRepository.acceptFriendRequest(token, email)
                Action.CancelRequest -> friendsRepository.cancelFriendRequest(token, email)
                Action.SendRequest -> friendsRepository.sendFriendRequest(token, email)
            }
        }catch (e:Throwable){
            Log.e("ActionOnFriendUseCase",e.message.toString())
        }
    }
    enum class Action{
        Delete,
        AcceptRequest,
        CancelRequest,
        SendRequest
    }
}