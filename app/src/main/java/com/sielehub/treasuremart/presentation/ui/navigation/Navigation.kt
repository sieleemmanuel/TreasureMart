package com.sielehub.treasuremart.presentation.ui.navigation

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
import com.sielehub.treasuremart.presentation.ui.account.ProfileScreen
import com.sielehub.treasuremart.presentation.ui.auth.AuthViewModel
import com.sielehub.treasuremart.presentation.ui.auth.ForgotPasswordScreen
import com.sielehub.treasuremart.presentation.ui.auth.LoginScreen
import com.sielehub.treasuremart.presentation.ui.auth.SignupScreen
import com.sielehub.treasuremart.presentation.ui.cart.CartsScreen
import com.sielehub.treasuremart.presentation.ui.checkout.CheckoutScreen
import com.sielehub.treasuremart.presentation.ui.dashboard.DashboardScreen
import com.sielehub.treasuremart.presentation.ui.notifications.NotificationsScreen
import com.sielehub.treasuremart.presentation.ui.onboarding.OnBoardingScreen
import com.sielehub.treasuremart.presentation.ui.onboarding.OnBoardingViewModel
import com.sielehub.treasuremart.presentation.ui.product.categories.CategoriesScreen
import com.sielehub.treasuremart.presentation.ui.product.categories.CategoryListState
import com.sielehub.treasuremart.presentation.ui.product.detail.ProductDetailScreen
import com.sielehub.treasuremart.presentation.ui.product.list.ProductListState
import com.sielehub.treasuremart.presentation.ui.product.list.ProductsScreen
import com.sielehub.treasuremart.presentation.ui.product.search.SearchScreen
import org.koin.androidx.compose.koinViewModel

private const val TAG = "Navigation"

@Composable
fun Navigation(
    onBoardingViewModel: OnBoardingViewModel = koinViewModel(),
    authViewModel: AuthViewModel = koinViewModel(),
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
                navController = navController,
                paddingValues = paddingValues,
                onSearchBarClick = {
                    navController.navigate(Route.Search)
                }
            )
        }
        composable<Route.Carts> {
            CartsScreen(
                paddingValues = paddingValues,
                navController = navController
            )
        }
        composable<Route.Checkout> {
            CheckoutScreen(
                paddingValues = paddingValues,
                navController = navController
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
        composable<Route.Favorites> {
            //val args = it.toRoute<Route.Favorites>()
            ProductsScreen(
                paddingValues = paddingValues,
                navController = navController
            )
        }
        composable<Route.Profile> {
            ProfileScreen(
                paddingValues = paddingValues,
                navController = navController
            )
        }
        composable<Route.Products> {
            val args = it.toRoute<Route.Products>()
            ProductsScreen(
                paddingValues = paddingValues,
                navController = navController,
                query = args.category ?: ""
            )
        }
        composable<Route.ProductDetail> {
            val args = it.toRoute<Route.ProductDetail>()
            ProductDetailScreen(
                paddingValues = paddingValues,
                navController = navController,
                productId = args.id
            )
        }
        composable<Route.Notifications> {
            NotificationsScreen(
                paddingValues = paddingValues,
                navController = navController
            )
        }
        composable<Route.Search> {
            SearchScreen(
                paddingValues = paddingValues,
            )
        }
    }

}