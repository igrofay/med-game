package ru.okei.med.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    //192.168.144.66:8080//
    //192.168.0.107:5121
    //176.28.64.201:3436
    private const val urlServer = "http://192.168.144.53:5121"
    private const val keySharedPreferences = "keySharedPreferences"

    @Provides
    @Singleton
    fun provideHttpClient() : HttpClient {
        val client = HttpClient(CIO){
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true

                })
            }
            install(HttpRequestRetry) {
                retryOnServerErrors(maxRetries = 5)
                exponentialDelay()
            }
            install(WebSockets) {
                contentConverter = KotlinxWebsocketSerializationConverter(Json)
            }
            install(HttpCache)
            expectSuccess = true
            defaultRequest{
                url(urlServer)
            }
        }
        client.plugin(HttpSend).intercept { request ->
            Log.e("intercept::request",request.url.toString())
            execute(request).apply {
                Log.e("intercept::answer",this.request.url.toString() +" "+  this.response.status.value.toString())
            }
        }
        return client
    }
    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application) : SharedPreferences {
        return app.getSharedPreferences(keySharedPreferences, Context.MODE_PRIVATE)
    }
}