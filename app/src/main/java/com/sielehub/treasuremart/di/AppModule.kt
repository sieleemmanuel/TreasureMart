package com.sielehub.treasuremart.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.sielehub.treasuremart.data.datastore.DataStoreManager
import com.sielehub.treasuremart.data.network.ApiServiceImp
import com.sielehub.treasuremart.data.repository.AuthRepositoryImpl
import com.sielehub.treasuremart.data.repository.StoreRepositoryImpl
import com.sielehub.treasuremart.domain.network.ApiService
import com.sielehub.treasuremart.domain.repository.AuthRepository
import com.sielehub.treasuremart.domain.repository.StoreRepository
import com.sielehub.treasuremart.domain.use_case.account.CreateUserUseCase
import com.sielehub.treasuremart.domain.use_case.account.GetUserUseCase
import com.sielehub.treasuremart.domain.use_case.account.LoginUseCase
import com.sielehub.treasuremart.domain.use_case.account.LogoutUseCase
import com.sielehub.treasuremart.domain.use_case.account.UpdateUserUseCase
import com.sielehub.treasuremart.domain.use_case.cart.CreateCartUseCase
import com.sielehub.treasuremart.domain.use_case.cart.GetCartUseCase
import com.sielehub.treasuremart.domain.use_case.cart.GetCartsUseCase
import com.sielehub.treasuremart.domain.use_case.cart.UpdateCartUseCase
import com.sielehub.treasuremart.domain.use_case.categories.GetCategoriesUseCase
import com.sielehub.treasuremart.domain.use_case.product.GetBestPickProductsUseCase
import com.sielehub.treasuremart.domain.use_case.product.GetProductUseCase
import com.sielehub.treasuremart.domain.use_case.product.GetProductsByCategoryUseCase
import com.sielehub.treasuremart.domain.use_case.product.GetProductsUseCase
import com.sielehub.treasuremart.domain.use_case.product.GetSuperDealsProductsUseCase
import com.sielehub.treasuremart.domain.use_case.product.search.AddSearchHistoryUseCase
import com.sielehub.treasuremart.domain.use_case.product.search.ClearSearchHistoryUseCase
import com.sielehub.treasuremart.domain.use_case.product.search.GetSearchHistoryUseCase
import com.sielehub.treasuremart.domain.use_case.product.search.GetSearchSuggestionsUseCase
import com.sielehub.treasuremart.domain.use_case.product.wishlist.AddToWishListUseCase
import com.sielehub.treasuremart.domain.use_case.product.wishlist.GetWishListUseCase
import com.sielehub.treasuremart.domain.use_case.product.wishlist.RemoveFromWishListUseCase
import com.sielehub.treasuremart.domain.use_case.settings.GetAppThemeUseCase
import com.sielehub.treasuremart.domain.use_case.settings.SetAppThemeUseCase
import com.sielehub.treasuremart.presentation.ui.account.AccountViewModel
import com.sielehub.treasuremart.presentation.ui.auth.AuthViewModel
import com.sielehub.treasuremart.presentation.ui.cart.CartViewModel
import com.sielehub.treasuremart.presentation.ui.dashboard.DashboardViewModel
import com.sielehub.treasuremart.presentation.ui.onboarding.OnBoardingViewModel
import com.sielehub.treasuremart.presentation.ui.product.categories.CategoriesViewModel
import com.sielehub.treasuremart.presentation.ui.product.detail.ProductDetailViewModel
import com.sielehub.treasuremart.presentation.ui.product.list.ProductsViewModel
import com.sielehub.treasuremart.presentation.ui.product.search.SearchViewModel
import com.sielehub.treasuremart.presentation.ui.product.wish.WishListViewModel
import com.sielehub.treasuremart.presentation.ui.settings.SettingsViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

object AppModule {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    @OptIn(ExperimentalSerializationApi::class)
    val appModule = module {
        single {
            HttpClient(Android) {
                install(Logging) {
                    level = LogLevel.ALL
                }
                install(ContentNegotiation) {
                    json(
                        json = Json {
                            explicitNulls = false
                            prettyPrint = true
                            isLenient = true
                            ignoreUnknownKeys = true
                        }
                    )
                }
            }
        }
        single<DataStore<Preferences>> { androidContext().dataStore }
        factory { DataStoreManager(get()) }

        singleOf(::ApiServiceImp) { bind<ApiService>() }
        singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
        singleOf(::StoreRepositoryImpl) { bind<StoreRepository>() }

        factory { GetProductsUseCase(get()) }
        factory { GetProductUseCase(get()) }
        factory { GetProductsByCategoryUseCase(get()) }
        factory { GetSuperDealsProductsUseCase(get()) }
        factory { GetBestPickProductsUseCase(get()) }
        factory { GetWishListUseCase(get()) }
        factory { RemoveFromWishListUseCase(get()) }
        factory { AddToWishListUseCase(get()) }
        factory { GetSearchSuggestionsUseCase(get()) }
        factory { GetSearchHistoryUseCase(get()) }
        factory { AddSearchHistoryUseCase(get()) }
        factory { ClearSearchHistoryUseCase(get()) }

        factory { GetCategoriesUseCase(get()) }

        factory { GetCartsUseCase(get()) }
        factory { GetCartUseCase(get()) }
        factory { CreateCartUseCase(get()) }
        factory { UpdateCartUseCase(get()) }

        factory { GetUserUseCase(get()) }
        factory { CreateUserUseCase(get()) }
        factory { UpdateUserUseCase(get()) }
        factory { LoginUseCase(get()) }

        factory { LogoutUseCase(get()) }

        factory { GetAppThemeUseCase(get()) }
        factory { SetAppThemeUseCase(get()) }

        viewModelOf(::OnBoardingViewModel)
        viewModelOf(::AuthViewModel)
        viewModelOf(::ProductsViewModel)
        viewModelOf(::ProductDetailViewModel)
        viewModelOf(::CartViewModel)
        viewModelOf(::CategoriesViewModel)
        viewModelOf(::AccountViewModel)
        viewModelOf(::DashboardViewModel)
        viewModelOf(::WishListViewModel)
        viewModelOf(::SearchViewModel)
        viewModelOf(::SettingsViewModel)
    }
}