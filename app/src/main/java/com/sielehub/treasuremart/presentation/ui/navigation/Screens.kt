package com.sielehub.treasuremart.presentation.ui.navigation

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
    data object Account : Route()

    @Serializable
    data object WishList : Route()

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
    data object SuperDealsProducts : Route()

    @Serializable
    data object Orders : Route()

    @Serializable
    data object Settings : Route()

    @Serializable
    data class Search(val query: String? = null) : Route()

    @Serializable
    data class Products(val productsQuery: String? = null) : Route()

    @Serializable
    data class ProductDetail(val id: Int) : Route()

}

