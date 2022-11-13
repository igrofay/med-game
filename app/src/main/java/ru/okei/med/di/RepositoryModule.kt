package ru.okei.med.di

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import ru.okei.med.data.repos.AuthRepositoryImpl
import ru.okei.med.data.repos.ProfileRepositoryImpl
import ru.okei.med.data.repos.TokenRepositoryImpl
import ru.okei.med.domain.repos.AuthRepository
import ru.okei.med.domain.repos.ProfileRepository
import ru.okei.med.domain.repos.TokenRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTokenRepository(
        sharedPreferences: SharedPreferences
    ): TokenRepository {
        return TokenRepositoryImpl(sharedPreferences)
    }

    @Provides
    fun provideAuthRepository(
        client: HttpClient
    ): AuthRepository {
        return AuthRepositoryImpl(client)
    }
    @Provides
    fun provideProfileRepository(
        client: HttpClient
    ): ProfileRepository {
        return ProfileRepositoryImpl(client)
    }

}