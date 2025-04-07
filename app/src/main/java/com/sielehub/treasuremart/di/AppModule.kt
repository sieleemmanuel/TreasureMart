package com.sielehub.treasuremart.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.sielehub.treasuremart.data.datastore.DataStoreManager
import com.sielehub.treasuremart.data.remote.StoreService
import com.sielehub.treasuremart.data.remote.StoreServiceImp
import com.sielehub.treasuremart.data.repository.AuthRepositoryImpl
import com.sielehub.treasuremart.data.repository.StoreRepositoryImpl
import com.sielehub.treasuremart.domain.repository.AuthRepository
import com.sielehub.treasuremart.domain.repository.StoreRepository
import com.sielehub.treasuremart.domain.use_case.cart.CreateCartUseCase
import com.sielehub.treasuremart.domain.use_case.cart.GetCartUseCase
import com.sielehub.treasuremart.domain.use_case.cart.GetCartsUseCase
import com.sielehub.treasuremart.domain.use_case.cart.UpdateCartUseCase
import com.sielehub.treasuremart.domain.use_case.categories.GetCategoriesUseCase
import com.sielehub.treasuremart.domain.use_case.product.GetProductUseCase
import com.sielehub.treasuremart.domain.use_case.product.GetProductsByCategoryUseCase
import com.sielehub.treasuremart.domain.use_case.product.GetProductsUseCase
import com.sielehub.treasuremart.domain.use_case.user.CreateUserUseCase
import com.sielehub.treasuremart.domain.use_case.user.GetUserUseCase
import com.sielehub.treasuremart.domain.use_case.user.LoginUseCase
import com.sielehub.treasuremart.domain.use_case.user.UpdateUserUseCase
import com.sielehub.treasuremart.presentation.auth.AuthViewModel
import com.sielehub.treasuremart.presentation.cart.CartViewModel
import com.sielehub.treasuremart.presentation.categories.CategoriesViewModel
import com.sielehub.treasuremart.presentation.onboarding.OnBoardingViewModel
import com.sielehub.treasuremart.presentation.product.ProductViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

object AppModule {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    val appModule = module {
        single {
            HttpClient(Android) {
                install(Logging) {
                    level = LogLevel.ALL
                }
                install(ContentNegotiation) {
                    json()
                }
            }
        }
        single<DataStore<Preferences>> { androidContext().dataStore }
        factory { DataStoreManager(get()) }

        singleOf(::StoreServiceImp) { bind<StoreService>() }
        singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
        singleOf(::StoreRepositoryImpl) { bind<StoreRepository>() }

        factory { GetProductsUseCase(get()) }
        factory { GetProductUseCase(get()) }
        factory { GetProductsByCategoryUseCase(get()) }

        factory { GetCategoriesUseCase(get()) }

        factory { GetCartsUseCase(get()) }
        factory { GetCartUseCase(get()) }
        factory { CreateCartUseCase(get()) }
        factory { UpdateCartUseCase(get()) }

        factory { GetUserUseCase(get()) }
        factory { CreateUserUseCase(get()) }
        factory { UpdateUserUseCase(get()) }
        factory { LoginUseCase(get()) }

        viewModelOf(::OnBoardingViewModel)
        viewModelOf(::AuthViewModel)
        viewModelOf(::ProductViewModel)
        viewModelOf(::CartViewModel)
        viewModelOf(::CategoriesViewModel)
    }
}