package com.sielehub.treasuremart.presentation.ui.navigation

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.sielehub.treasuremart.presentation.base.MainViewModel
import com.sielehub.treasuremart.presentation.ui.account.AccountScreen
import com.sielehub.treasuremart.presentation.ui.address.ShippingAddressScreen
import com.sielehub.treasuremart.presentation.ui.auth.AuthViewModel
import com.sielehub.treasuremart.presentation.ui.auth.ForgotPasswordScreen
import com.sielehub.treasuremart.presentation.ui.auth.LoginScreen
import com.sielehub.treasuremart.presentation.ui.auth.SignupScreen
import com.sielehub.treasuremart.presentation.ui.cart.CartViewModel
import com.sielehub.treasuremart.presentation.ui.cart.CartsScreen
import com.sielehub.treasuremart.presentation.ui.checkout.CheckoutScreen
import com.sielehub.treasuremart.presentation.ui.dashboard.DashboardScreen
import com.sielehub.treasuremart.presentation.ui.dashboard.DashboardViewModel
import com.sielehub.treasuremart.presentation.ui.notifications.NotificationsScreen
import com.sielehub.treasuremart.presentation.ui.onboarding.OnBoardingScreen
import com.sielehub.treasuremart.presentation.ui.onboarding.OnBoardingViewModel
import com.sielehub.treasuremart.presentation.ui.orders.OrdersScreen
import com.sielehub.treasuremart.presentation.ui.product.categories.CategoriesScreen
import com.sielehub.treasuremart.presentation.ui.product.categories.CategoryListState
import com.sielehub.treasuremart.presentation.ui.product.detail.ProductDetailScreen
import com.sielehub.treasuremart.presentation.ui.product.list.ProductListState
import com.sielehub.treasuremart.presentation.ui.product.list.ProductsScreen
import com.sielehub.treasuremart.presentation.ui.product.list.SuperDealProductsScreen
import com.sielehub.treasuremart.presentation.ui.product.search.SearchScreen
import com.sielehub.treasuremart.presentation.ui.product.wish.WishListScreen
import com.sielehub.treasuremart.presentation.ui.product.wish.WishListViewModel
import com.sielehub.treasuremart.presentation.ui.settings.SettingsScreen
import org.koin.androidx.compose.koinViewModel

private const val TAG = "Navigation"

@Composable
fun Navigation(
    onBoardingViewModel: OnBoardingViewModel = koinViewModel(),
    authViewModel: AuthViewModel = koinViewModel(),
    dashboardViewModel: DashboardViewModel = koinViewModel(),
    mainViewModel: MainViewModel = koinViewModel(),
    cartViewModel: CartViewModel = koinViewModel(),
    wishListViewModel: WishListViewModel = koinViewModel(),
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    val onBoardingDone by onBoardingViewModel.onBoardingDone.collectAsState()
    val loginToken by authViewModel.authToken.collectAsState()
    val startDestination = if (onBoardingDone) {
        if (loginToken.isNullOrEmpty()) Route.Auth else Route.Dashboard
    } else Route.OnBoarding
    var isLoginAuthStartDest by remember { mutableStateOf(true) }

    LaunchedEffect(loginToken) {
        if (onBoardingDone && loginToken.isNullOrEmpty()) {
            navController.navigate(Route.Auth) {
                Route.toRoute(navController.currentDestination?.route)?.let {
                    popUpTo(it) {
                        inclusive = true
                    }
                }
            }
        }
    }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Route.OnBoarding> {
            OnBoardingScreen(
                onBoardingViewModel = onBoardingViewModel,
                paddingValues = { paddingValues },
                onFinish = { isLogin ->
                    isLoginAuthStartDest = isLogin
                    navController.navigate(Route.Auth)
                }
            )
        }

        navigation<Route.Auth>(
            startDestination = if (isLoginAuthStartDest) Route.Login else Route.Signup
        ) {
            composable<Route.Login> {
                LoginScreen(
                    authViewModel = authViewModel,
                    paddingValues = { paddingValues },
                    onSignUp = {
                        navController.navigate(Route.Signup)
                    },
                    navController = { navController },
                    onForgotPassword = {
                        navController.navigate(Route.ForgotPassword)
                    }
                )
            }
            composable<Route.Signup> {
                SignupScreen(
                    authViewModel = authViewModel,
                    paddingValues = { paddingValues },
                    naveController = { navController }
                )
            }

            composable<Route.ForgotPassword> {
                ForgotPasswordScreen(
                    navController = navController,
                    paddingValues = paddingValues
                )
            }
        }
        composable<Route.Dashboard> {
            DashboardScreen(
                dashboardViewModel = dashboardViewModel,
                paddingValues = paddingValues,
                onSearchBarClick = {
                    navController.navigate(Route.Search())
                },
                onOpenNotification = {
                    navController.navigate(Route.Notifications)
                },
                onNavigateToProductDetails = {
                    navController.navigate(Route.ProductDetail(it))
                },
                onOpenProductDeals = {
                    navController.navigate(Route.SuperDealsProducts)
                }
            )
        }
        composable<Route.Carts> {
            CartsScreen(
                mainViewModel = mainViewModel,
                cartViewModel = cartViewModel,
                paddingValues = paddingValues,
                onNavigateToCheckout = {
                    navController.navigate(Route.Checkout)
                },
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable<Route.Categories> {
            CategoriesScreen(
                paddingValues = paddingValues,
                navController = navController,
                categoryListState = CategoryListState(),
                productsListState = ProductListState()
            )
        }
        composable<Route.WishList> {
            //val args = it.toRoute<Route.Favorites>()
            WishListScreen(
                wishListViewModel = wishListViewModel,
                paddingValues = paddingValues,
                onNavigateBack = {
                    navController.navigateUp()
                },
                onNavigateToProductDetail = {
                    navController.navigate(Route.ProductDetail(it))
                }
            )
        }
        composable<Route.Orders> {
            OrdersScreen(
                paddingValues = paddingValues,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable<Route.Checkout> {
            CheckoutScreen(
                paddingValues = paddingValues,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onEditAddress = {
                    navController.navigate(Route.ShippingAddress)
                }
            )
        }

        composable<Route.ShippingAddress> {
            ShippingAddressScreen(
                paddingValues = paddingValues,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<Route.Account> {
            AccountScreen(
                paddingValues = paddingValues,
                onNavigateToNotifications = {
                    navController.navigate(Route.Notifications)
                },
                onNavigateToSettings = {
                    navController.navigate(Route.Settings)
                },
                onNavigateToOrders = {
                    navController.navigate(Route.Orders)
                },
                onNavigateToAddress = {
                    navController.navigate(Route.ShippingAddress)
                }
            )
        }

        composable<Route.Settings> {
            SettingsScreen(
                paddingValues = paddingValues,
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable<Route.Products> {
            val args = it.toRoute<Route.Products>()
            ProductsScreen(
                paddingValues = paddingValues,
                productsQuery = args.productsQuery ?: "",
                onNavigateBack = {
                    navController.navigateUp()
                },
                onNavigateToProductDetail = {
                    navController.navigate(Route.ProductDetail(it))
                },
                onNavigateToCart = {
                    navController.navigate(Route.Carts)
                },
                onSearchBarClick = {
                    navController.navigate(Route.Search())
                }
            )
        }
        composable<Route.ProductDetail> {
            val args = it.toRoute<Route.ProductDetail>()
            ProductDetailScreen(
                mainViewModel = mainViewModel,
                paddingValues = paddingValues,
                productId = args.id,
                onNavigateBack = {
                    navController.navigateUp()
                },
                onNavigateToCart = {
                    navController.navigate(Route.Carts)
                },
                onNavigateToCheckout = {
                    navController.navigate(Route.Checkout)
                }
            )
        }
        composable<Route.SuperDealsProducts> {
            SuperDealProductsScreen(
                dashboardViewModel = dashboardViewModel,
                paddingValues = paddingValues,
                onNavigateBack = {
                    navController.navigateUp()
                },
                onNavigateToProductDetail = { productId, discount ->
                    navController.navigate(Route.ProductDetail(productId))
                }
            )
        }
        composable<Route.Notifications> {
            NotificationsScreen(
                paddingValues = paddingValues,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable<Route.Search> {
            // val args = it.toRoute<Route.Search>()
            SearchScreen(
                paddingValues = paddingValues,
                onNavigateBack = {
                    navController.navigateUp()
                },
                onSearch = {
                    Log.d(TAG, "search query: $it")
                    navController.navigate(Route.Products(it))
                }
            )
        }
    }

}