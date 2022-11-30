package ru.okei.med.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import ru.okei.med.data.repos.*
import ru.okei.med.domain.repos.*
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
    @Singleton
    fun provideProfileRepository(
        client: HttpClient,
    ): ProfileRepository {
        return ProfileRepositoryImpl(client)
    }

    @Provides
    fun provideFriendsRepository(
        client: HttpClient
    ) : FriendsRepository {
        return FriendsRepositoryImpl(client)
    }

    @Provides
    fun provideRatingRepository(
        client: HttpClient
    ): RatingRepository{
        return RatingRepositoryImpl(client)
    }
}