package com.sielehub.treasuremart.domain.repository

import com.sielehub.treasuremart.domain.model.WishProduct
import kotlinx.coroutines.flow.Flow

interface WishRepository {

    suspend fun getWishlist(): Flow<List<WishProduct>>

    suspend fun addToWishlist(wishProduct: WishProduct)

    suspend fun checkIsWish(productId: Int): Boolean

    suspend fun removeFromWishlist(productId: Int)


}