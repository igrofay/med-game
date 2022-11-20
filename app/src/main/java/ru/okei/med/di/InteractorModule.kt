package ru.okei.med.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.okei.med.domain.interactor.FightWithEnemyInteractor
import ru.okei.med.domain.repos.BattleRepository
import ru.okei.med.domain.repos.TokenRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InteractorModule {
    @Provides
    fun provideFightWithEnemyInteractor(
        tokenRepository: TokenRepository,
        battleRepository: BattleRepository
    ) = FightWithEnemyInteractor(tokenRepository, battleRepository)
}