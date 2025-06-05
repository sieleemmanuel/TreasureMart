package com.sielehub.treasuremart.data.repository

import com.sielehub.treasuremart.data.datastore.DataStoreManager
import com.sielehub.treasuremart.data.local.database.CartDao
import com.sielehub.treasuremart.domain.model.Cart
import com.sielehub.treasuremart.domain.network.ApiService
import com.sielehub.treasuremart.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

class CartRepositoryImpl(
    private val apiService: ApiService,
    private val cartDao: CartDao,
    private val dataStoreManager: DataStoreManager
) : CartRepository {


    override suspend fun getCarts(): List<Cart> {
        val carts = apiService.getCarts().map { it.toCart() }
        val cachedCarts = cartDao.getCarts()
        if (cachedCarts.isEmpty()) {
            carts.forEach { cartDao.insertCart(it) }
        }
        return cartDao.getCarts()
    }

    override fun getCart(): Flow<Cart?> {
        return dataStoreManager.currentUserId.flatMapLatest { userId ->
            if (userId > 0) {
                cartDao.getCart(userId)
            } else {
                flowOf(null)
            }
        }
    }

    override suspend fun createCart(cart: Cart): Cart? =
        apiService.createCart(cart)?.toCart()


    override suspend fun updateCart(cart: Cart): Int =
        cartDao.updateCart(cart.id, cart.products)

}