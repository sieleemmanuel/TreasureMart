package com.sielehub.treasuremart.domain.repository

import com.sielehub.treasuremart.domain.model.Cart
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    suspend fun createCart(cart: Cart): Cart?

    suspend fun updateCart(cart: Cart): Int

    suspend fun getCarts(): Flow<List<Cart>>

    fun getCart(): Flow<Cart?>

    suspend fun getCartAmount(): Flow<Double>


}