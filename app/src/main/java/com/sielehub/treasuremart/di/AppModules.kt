package com.sielehub.treasuremart.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.sielehub.treasuremart.data.datastore.DataStoreManager
import com.sielehub.treasuremart.data.local.database.StoreDb
import com.sielehub.treasuremart.data.network.ApiServiceImp
import com.sielehub.treasuremart.data.repository.AuthRepositoryImpl
import com.sielehub.treasuremart.data.repository.CartRepositoryImpl
import com.sielehub.treasuremart.data.repository.NotificationRepositoryImpl
import com.sielehub.treasuremart.data.repository.OrderRepositoryImpl
import com.sielehub.treasuremart.data.repository.StoreRepositoryImpl
import com.sielehub.treasuremart.data.repository.WishRepositoryImpl
import com.sielehub.treasuremart.domain.network.ApiService
import com.sielehub.treasuremart.domain.repository.AuthRepository
import com.sielehub.treasuremart.domain.repository.CartRepository
import com.sielehub.treasuremart.domain.repository.NotificationRepository
import com.sielehub.treasuremart.domain.repository.OrderRepository
import com.sielehub.treasuremart.domain.repository.StoreRepository
import com.sielehub.treasuremart.domain.repository.WishRepository
import com.sielehub.treasuremart.domain.use_case.account.CreateUserUseCase
import com.sielehub.treasuremart.domain.use_case.account.GetCurrentUserIdUseCase
import com.sielehub.treasuremart.domain.use_case.account.GetUserUseCase
import com.sielehub.treasuremart.domain.use_case.account.LoginUseCase
import com.sielehub.treasuremart.domain.use_case.account.LogoutUseCase
import com.sielehub.treasuremart.domain.use_case.account.UpdateCurrentUserIdUseCase
import com.sielehub.treasuremart.domain.use_case.account.UpdateUserUseCase
import com.sielehub.treasuremart.domain.use_case.cart.AddProductToCartUseCase
import com.sielehub.treasuremart.domain.use_case.cart.CheckCartProductUseCase
import com.sielehub.treasuremart.domain.use_case.cart.CreateCartUseCase
import com.sielehub.treasuremart.domain.use_case.cart.GetCartAmountUseCase
import com.sielehub.treasuremart.domain.use_case.cart.GetCartUseCase
import com.sielehub.treasuremart.domain.use_case.cart.GetCartsUseCase
import com.sielehub.treasuremart.domain.use_case.cart.UpdateCartProductQuantityUseCase
import com.sielehub.treasuremart.domain.use_case.cart.UpdateCartProductsUseCase
import com.sielehub.treasuremart.domain.use_case.categories.GetCategoriesUseCase
import com.sielehub.treasuremart.domain.use_case.checkout.PlaceOrderUseCase
import com.sielehub.treasuremart.domain.use_case.notifications.DeleteNotificationUseCase
import com.sielehub.treasuremart.domain.use_case.notifications.GetNotificationsUseCase
import com.sielehub.treasuremart.domain.use_case.notifications.InsertNotificationUseCase
import com.sielehub.treasuremart.domain.use_case.notifications.UpdateReadStatusUseCase
import com.sielehub.treasuremart.domain.use_case.orders.DeleteOrderUseCase
import com.sielehub.treasuremart.domain.use_case.orders.GetOrderUseCase
import com.sielehub.treasuremart.domain.use_case.orders.GetOrdersUseCase
import com.sielehub.treasuremart.domain.use_case.product.GetBestPickProductsUseCase
import com.sielehub.treasuremart.domain.use_case.product.GetProductUseCase
import com.sielehub.treasuremart.domain.use_case.product.GetProductsByCategoryUseCase
import com.sielehub.treasuremart.domain.use_case.product.GetProductsUseCase
import com.sielehub.treasuremart.domain.use_case.product.GetSuperDealsProductsUseCase
import com.sielehub.treasuremart.domain.use_case.search.AddSearchHistoryUseCase
import com.sielehub.treasuremart.domain.use_case.search.ClearSearchHistoryUseCase
import com.sielehub.treasuremart.domain.use_case.search.GetSearchHistoryUseCase
import com.sielehub.treasuremart.domain.use_case.search.GetSearchSuggestionsUseCase
import com.sielehub.treasuremart.domain.use_case.settings.GetAppThemeUseCase
import com.sielehub.treasuremart.domain.use_case.settings.SetAppThemeUseCase
import com.sielehub.treasuremart.domain.use_case.wishlist.AddToWishListUseCase
import com.sielehub.treasuremart.domain.use_case.wishlist.CheckIsWishUseCase
import com.sielehub.treasuremart.domain.use_case.wishlist.GetWishListUseCase
import com.sielehub.treasuremart.domain.use_case.wishlist.RemoveFromWishListUseCase
import com.sielehub.treasuremart.presentation.base.MainViewModel
import com.sielehub.treasuremart.presentation.ui.account.AccountViewModel
import com.sielehub.treasuremart.presentation.ui.auth.AuthViewModel
import com.sielehub.treasuremart.presentation.ui.cart.CartViewModel
import com.sielehub.treasuremart.presentation.ui.checkout.CheckoutViewModel
import com.sielehub.treasuremart.presentation.ui.dashboard.DashboardViewModel
import com.sielehub.treasuremart.presentation.ui.onboarding.OnBoardingViewModel
import com.sielehub.treasuremart.presentation.ui.orders.OrdersViewModel
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
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

object AppModules {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    @OptIn(ExperimentalSerializationApi::class)
    val appModule = module {
        single<DataStore<Preferences>> { androidContext().dataStore }
        single { StoreDb.getInstance(androidContext()).storeDao }

        factory { DataStoreManager(get()) }

        factory { GetCategoriesUseCase(get()) }

        factory { GetAppThemeUseCase(get()) }
        factory { SetAppThemeUseCase(get()) }

        viewModelOf(::MainViewModel)
        viewModelOf(::OnBoardingViewModel)
        viewModelOf(::DashboardViewModel)
        viewModelOf(::SettingsViewModel)
    }

    @OptIn(ExperimentalSerializationApi::class)
    val networkModule = module {
        single {
            HttpClient(Android) {
                install(Logging) {
                    level = LogLevel.ALL
                }
                install(ContentNegotiation) {
                    register(
                        contentType = ContentType.Any,
                        converter = KotlinxSerializationConverter(
                            format = Json {
                                explicitNulls = false
                                prettyPrint = true
                                isLenient = true
                                ignoreUnknownKeys = true
                            }
                        )
                    )
                }
            }
        }
        singleOf(::ApiServiceImp) { bind<ApiService>() }
    }

    val authModule = module {
        singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
        factory { GetUserUseCase(get()) }
        factory { CreateUserUseCase(get()) }
        factory { UpdateCurrentUserIdUseCase(get(), get()) }
        factory { GetCurrentUserIdUseCase(get()) }

        factory { UpdateUserUseCase(get()) }
        factory { LoginUseCase(get()) }

        factory { LogoutUseCase(get()) }
        viewModelOf(::AuthViewModel)
        viewModelOf(::AccountViewModel)
    }

    val productsModule = module {
        singleOf(::StoreRepositoryImpl) { bind<StoreRepository>() }

        factory { GetProductsUseCase(get()) }
        factory { GetProductUseCase(get()) }
        factory { GetProductsByCategoryUseCase(get()) }
        factory { GetSuperDealsProductsUseCase(get()) }
        factory { GetBestPickProductsUseCase(get()) }

        factory { GetSearchSuggestionsUseCase(get()) }
        factory { GetSearchHistoryUseCase(get()) }
        factory { AddSearchHistoryUseCase(get()) }
        factory { ClearSearchHistoryUseCase(get()) }

        viewModelOf(::ProductsViewModel)
        viewModelOf(::ProductDetailViewModel)
        viewModelOf(::SearchViewModel)
        viewModelOf(::CategoriesViewModel)
    }

    val wishModule = module {
        single { StoreDb.getInstance(androidContext()).wishDao }
        singleOf(::WishRepositoryImpl) { bind<WishRepository>() }
        factory { GetWishListUseCase(get()) }
        factory { RemoveFromWishListUseCase(get()) }
        factory { AddToWishListUseCase(get()) }
        factory { CheckIsWishUseCase(get()) }

        viewModelOf(::WishListViewModel)

    }

    val cartModule = module {
        single { StoreDb.getInstance(androidContext()).cartDao }
        singleOf(::CartRepositoryImpl) { bind<CartRepository>() }

        factory { GetCartsUseCase(get()) }
        factory { GetCartUseCase(get()) }
        factory { CreateCartUseCase(get()) }
        factory { UpdateCartProductsUseCase(get()) }
        factory { UpdateCartProductQuantityUseCase(get()) }
        factory { GetCartAmountUseCase(get()) }
        factory { AddProductToCartUseCase(get()) }
        factory { CheckCartProductUseCase(get()) }

        viewModelOf(::CartViewModel)
    }

    val ordersModule = module {
        single { StoreDb.getInstance(androidContext()).ordersDao }
        singleOf(::OrderRepositoryImpl) { bind<OrderRepository>() }

        factory { PlaceOrderUseCase(get()) }
        factory { GetOrdersUseCase(get()) }
        factory { GetOrderUseCase(get()) }
        factory { DeleteOrderUseCase(get()) }

        viewModelOf(::CheckoutViewModel)
        viewModelOf(::OrdersViewModel)
    }

    val notificationModule = module {
        single { StoreDb.getInstance(androidContext()).notificationsDao }
        singleOf(::NotificationRepositoryImpl) { bind<NotificationRepository>() }
        
        factory { GetNotificationsUseCase(get()) }
        factory { InsertNotificationUseCase(get()) }
        factory { UpdateReadStatusUseCase(get()) }
        factory { DeleteNotificationUseCase(get()) }

    }
}