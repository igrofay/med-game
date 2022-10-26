package ru.okei.med.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    //192.168.144.66:8080
    //176.28.64.201:3436
    private const val urlServer = "http://176.28.64.201:3436"
    private const val keySharedPreferences = "keySharedPreferences"

    @Provides
    @Singleton
    fun provideHttpClient() : HttpClient {
        return HttpClient(CIO){
            install(HttpRequestRetry) {
                retryOnServerErrors(maxRetries = 5)
                exponentialDelay()
            }
            install(HttpCache)
            install(ContentNegotiation) {
                json(Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            expectSuccess = true
            defaultRequest{
                url(urlServer)
            }
        }
    }
    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application) : SharedPreferences {
        return app.getSharedPreferences(keySharedPreferences, Context.MODE_PRIVATE)
    }
}