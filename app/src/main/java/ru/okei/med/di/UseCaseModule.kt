package ru.okei.med.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import ru.okei.med.data.repos.BattleRepositoryImpl
import ru.okei.med.domain.repos.AuthRepository
import ru.okei.med.domain.repos.BattleRepository
import ru.okei.med.domain.repos.ProfileRepository
import ru.okei.med.domain.repos.TokenRepository
import ru.okei.med.domain.use_case.auth.RestoreSessionUseCase
import ru.okei.med.domain.use_case.auth.SignInUseCase
import ru.okei.med.domain.use_case.auth.SignUpUseCase
import ru.okei.med.domain.use_case.profile.ChangeImageUseCase
import ru.okei.med.domain.use_case.profile.GetProfileUseCase

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideSignInUseCase(
        tokenRepository: TokenRepository,
        authRepository: AuthRepository,
        appContext: Application
    ) : SignInUseCase {
        return SignInUseCase(tokenRepository, authRepository, appContext)
    }
    @Provides
    fun provideSignUpUseCase(
        tokenRepository: TokenRepository,
        authRepository: AuthRepository,
        appContext: Application
    ) : SignUpUseCase {
        return SignUpUseCase(tokenRepository, authRepository, appContext)
    }
    @Provides
    fun provideRestoreSessionUseCase(
        tokenRepository: TokenRepository,
        authRepository: AuthRepository,
    ) : RestoreSessionUseCase {
        return RestoreSessionUseCase(tokenRepository, authRepository)
    }

    @Provides
    fun provideGetProfileUseCase(
        tokenRepository: TokenRepository,
        profileRepository: ProfileRepository,
        appContext: Application
    ): GetProfileUseCase {
        return GetProfileUseCase(tokenRepository, profileRepository,appContext)
    }
    @Provides
    fun provideBattleRepositoryImpl(
        client: HttpClient
    ) : BattleRepository {
        return BattleRepositoryImpl(client)
    }
    @Provides
    fun provideChangeImageUseCase(
        tokenRepository: TokenRepository,
        profileRepository: ProfileRepository,
        appContext: Application
    ) = ChangeImageUseCase(tokenRepository,profileRepository,appContext)
}