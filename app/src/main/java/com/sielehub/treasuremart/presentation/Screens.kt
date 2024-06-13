package com.sielehub.treasuremart.presentation

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
    object Dashboard : Route()

    @Serializable
    object Profile : Route()

    @Serializable
    object Favorites : Route()

    @Serializable
    object Checkout : Route()

    @Serializable
    object RatingsAndReviews : Route()

    @Serializable
    object Carts : Route()

    @Serializable
    object Categories : Route()

    @Serializable
    object Notifications : Route()

    @Serializable
    data class Products(val category: String? = null) : Route()

    @Serializable
    data class ProductDetail(val id: Int) : Route()
}

