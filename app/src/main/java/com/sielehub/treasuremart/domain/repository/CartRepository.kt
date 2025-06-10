package com.sielehub.treasuremart.domain.repository

import com.sielehub.treasuremart.domain.model.Cart
import com.sielehub.treasuremart.domain.model.CartProduct
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    suspend fun createCart(cart: Cart): Cart?

    suspend fun updateCart(cartProducts: CartProduct): Boolean

    suspend fun checkCartProduct(cartProduct: CartProduct): Boolean

    suspend fun updateCartProductQuantity(cartProduct: CartProduct, isIncrease: Boolean): Boolean

    suspend fun getCarts(): Flow<List<Cart>>

    fun getCart(): Flow<Cart?>

    suspend fun getCartAmount(): Flow<Double>


}