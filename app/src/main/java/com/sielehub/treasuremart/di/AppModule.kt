package com.sielehub.treasuremart.di

import com.sielehub.treasuremart.data.remote.PostServiceImp
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import org.koin.dsl.module

object AppModule {
    val appModule = module {
        single {
            HttpClient(Android){
            install(Logging){
                level = LogLevel.ALL
            }
            install(ContentNegotiation){
                json()
            }
            }
        }
        factory { PostServiceImp(get()) }
    }
}