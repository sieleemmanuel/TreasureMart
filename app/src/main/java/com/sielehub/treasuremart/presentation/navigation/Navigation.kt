package com.sielehub.treasuremart.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.sielehub.treasuremart.presentation.auth.ForgotPasswordScreen
import com.sielehub.treasuremart.presentation.auth.LoginScreen
import com.sielehub.treasuremart.presentation.auth.SignupScreen
import com.sielehub.treasuremart.presentation.cart.CartsScreen
import com.sielehub.treasuremart.presentation.cart.CheckoutScreen
import com.sielehub.treasuremart.presentation.categories.CategoriesScreen
import com.sielehub.treasuremart.presentation.categories.CategoryListState
import com.sielehub.treasuremart.presentation.common.SearchScreen
import com.sielehub.treasuremart.presentation.dashboard.Dashboard
import com.sielehub.treasuremart.presentation.notifications.NotificationsScreen
import com.sielehub.treasuremart.presentation.onboarding.OnBoardingScreen
import com.sielehub.treasuremart.presentation.onboarding.OnBoardingViewModel
import com.sielehub.treasuremart.presentation.product.ProductListState
import com.sielehub.treasuremart.presentation.product.ProductsScreen
import com.sielehub.treasuremart.presentation.product_detail.ProductDetailScreen
import com.sielehub.treasuremart.presentation.user.ProfileScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun Navigation(
    viewModel: OnBoardingViewModel = koinViewModel(),
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    val onBoardingDone by viewModel.onBoardingDone.collectAsState()
    val startDestination = if (onBoardingDone) Route.Dashboard else Route.OnBoarding

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Route.OnBoarding> {
            OnBoardingScreen(
                paddingValues = { paddingValues },
                onFinish = {
                    navController.navigate(Route.Auth)
                }
            )
        }

        navigation<Route.Auth>(startDestination = Route.Signup) {
            composable<Route.Login> {
                LoginScreen(
                    paddingValues = { paddingValues },
                    onSignUp = {
                        navController.navigate(Route.Signup)
                    },
                    onForgotPassword = {
                        navController.navigate(Route.ForgotPassword)
                    }
                )
            }
            composable<Route.Signup> {
                SignupScreen()
            }

            composable<Route.ForgotPassword> {
                ForgotPasswordScreen()
            }
        }
        composable<Route.Dashboard> {
            Dashboard(
                navController = navController,
                paddingValues = paddingValues
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
            val args = it.toRoute<Route.Favorites>()
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
                navController = navController
            )
        }
    }

}