package com.sielehub.treasuremart.core

class Constants {
    object HttpRoutes {
        //private const val BASE_URL ="https://api.escuelajs.co/api/v1" without cart
        private const val BASE_URL ="https://fakestoreapi.com"
        const val PRODUCTS_ENDPOINT = "$BASE_URL/products"
        const val PRODUCT_ENDPOINT = "$BASE_URL/products/"
        const val CATEGORY_PRODUCTS_ENDPOINT = "$BASE_URL/products/category/"
        const val CATEGORIES_ENDPOINT = "$BASE_URL/categories"
        const val CARTS_ENDPOINT = "$BASE_URL/carts"
        const val USERS_ENDPOINT = "$BASE_URL/users"
        const val CREATE_USER_ENDPOINT = "$BASE_URL/users/"
        const val AUTH_ENDPOINT = "$BASE_URL/auth/login/"
    }
}