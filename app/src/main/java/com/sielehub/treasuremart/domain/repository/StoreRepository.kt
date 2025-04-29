package com.sielehub.treasuremart.domain.repository

import com.sielehub.treasuremart.domain.model.Cart
import com.sielehub.treasuremart.domain.model.Notification
import com.sielehub.treasuremart.domain.model.Product

interface StoreRepository {

    suspend fun getProducts(): List<Product>

    suspend fun getProductsByCategory(category: String): List<Product>

    suspend fun getSuperDealsProducts(): List<Product>

    suspend fun getBestPickProducts(): List<Product>

    suspend fun getProduct(id: Int): Product?

    suspend fun getCategories(): List<String>

    suspend fun getSearchedProducts(query: String): List<Product>

    suspend fun getWishlist(): List<Product>

    suspend fun addToWishlist(product: Product): Boolean

    suspend fun removeFromWishlist(productId: Int): Boolean

    suspend fun createCart(cart: Cart): Cart?

    suspend fun updateCart(cart: Cart): Cart?

    suspend fun getCarts(): List<Cart>

    suspend fun getCart(id: Int): Cart?

    suspend fun getNotifications(): List<Notification>

}