package com.sielehub.treasuremart.di

import android.os.Build
import androidx.annotation.RequiresExtension
import com.sielehub.treasuremart.data.remote.StoreServiceImp
import com.sielehub.treasuremart.data.repository.StoreRepositoryImpl
import com.sielehub.treasuremart.domain.use_case.cart.CreateCartUseCase
import com.sielehub.treasuremart.domain.use_case.cart.GetCartUseCase
import com.sielehub.treasuremart.domain.use_case.cart.GetCartsUseCase
import com.sielehub.treasuremart.domain.use_case.cart.UpdateCartUseCase
import com.sielehub.treasuremart.domain.use_case.categories.GetCategoriesUseCase
import com.sielehub.treasuremart.domain.use_case.product.GetProductUseCase
import com.sielehub.treasuremart.domain.use_case.product.GetProductsByCategoryUseCase
import com.sielehub.treasuremart.domain.use_case.product.GetProductsUseCase
import com.sielehub.treasuremart.domain.use_case.user.GetUserUseCase
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import org.koin.dsl.module

object AppModule {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
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

        factory { StoreServiceImp(get() ) }
        factory { StoreRepositoryImpl(get()) }

        factory { GetProductsUseCase(get()) }
        factory { GetProductUseCase(get()) }
        factory { GetProductsByCategoryUseCase(get()) }

        factory { GetCategoriesUseCase(get()) }

        factory { GetCartsUseCase(get()) }
        factory { GetCartUseCase(get()) }
        factory { CreateCartUseCase(get()) }
        factory { UpdateCartUseCase(get()) }

        factory { GetUserUseCase(get()) }
        factory { CreateCartUseCase(get()) }
        factory { UpdateCartUseCase(get()) }
    }
}