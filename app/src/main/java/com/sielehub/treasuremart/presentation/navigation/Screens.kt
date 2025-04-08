package com.sielehub.treasuremart.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Route {
    companion object {
        fun toRoute(stringRoute: String?): Route? {
            return Route::class.sealedSubclasses.firstOrNull {
                stringRoute?.contains(it.qualifiedName.toString()) == true
            }?.objectInstance
        }
    }

    @Serializable
    data object OnBoarding : Route()

    @Serializable
    data object Auth : Route()

    @Serializable
    data object Login : Route()

    @Serializable
    data object ForgotPassword : Route()

    @Serializable
    data object Signup : Route()

    @Serializable
    data object Dashboard : Route()

    @Serializable
    data object Profile : Route()

    @Serializable
    data object Favorites : Route()

    @Serializable
    data object Checkout : Route()

    @Serializable
    data object RatingsAndReviews : Route()

    @Serializable
    data object Carts : Route()

    @Serializable
    data object Categories : Route()

    @Serializable
    data object Notifications : Route()

    @Serializable
    data object Search : Route()

    @Serializable
    data class Products(val category: String? = null) : Route()

    @Serializable
    data class ProductDetail(val id: Int) : Route()
}

