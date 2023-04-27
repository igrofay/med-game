package ru.okei.med.domain.use_case.battle

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import ru.okei.med.domain.model.RequestForFight
import ru.okei.med.domain.repos.BattleRepository
import ru.okei.med.domain.repos.TokenRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestFightRequestsInteractor @Inject constructor(
    private val battleRepository: BattleRepository,
    private val tokenRepository: TokenRepository,
) {

    fun executeFlow() = flow {
        while (true){
            val requestForFight = battleRepository
                .getRequestForFight(tokenRepository.access)
//            if (lastState?.tokenRoom != requestForFight?.tokenRoom){
//                lastState?.tokenRoom?.let {
//                    battleRepository.refuseRequest(tokenRepository.access, it)
//                }
//            }
//            lastState = requestForFight
            emit(requestForFight)
            delay(2_000)
        }
    }


    suspend fun refuse(tokenRoom: String) {
        battleRepository.refuseRequest(tokenRepository.access, tokenRoom)
    }

}