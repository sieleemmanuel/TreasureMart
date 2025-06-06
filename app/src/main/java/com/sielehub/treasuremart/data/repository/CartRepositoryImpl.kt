package com.sielehub.treasuremart.data.repository

import android.util.Log
import com.sielehub.treasuremart.data.datastore.DataStoreManager
import com.sielehub.treasuremart.data.local.database.CartDao
import com.sielehub.treasuremart.domain.model.Cart
import com.sielehub.treasuremart.domain.network.ApiService
import com.sielehub.treasuremart.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

class CartRepositoryImpl(
    private val apiService: ApiService,
    private val cartDao: CartDao,
    private val dataStoreManager: DataStoreManager
) : CartRepository {

    override suspend fun getCarts(): Flow<List<Cart>> {
        var carts: List<Cart> = emptyList()
        dataStoreManager.currentUserId.collectLatest { userId ->
            carts = if (userId > 0) {
                val cachedCart = cartDao.getCart(dataStoreManager.currentUserId.first()).first()
                if (cachedCart == null) {
                    val carts = apiService.getCarts().map { it.toCart() }
                    Log.d("CartRepositoryImpl", "Remote Carts: $carts")
                    val currentCart = carts.find {
                        it.userId == dataStoreManager.currentUserId.first()
                    }
                    Log.d("CartRepositoryImpl", "currentCart: $currentCart")
                    currentCart?.products?.map {
                        val product = apiService.getProduct(it.productId)
                        it.price = product?.price
                        it.title = product?.title
                        it.image = product?.image
                        it.category = product?.category
                        it.description = product?.description
                    }
                    Log.d("CartRepositoryImpl", "currentCart Prods: ${currentCart?.products}")
                    currentCart?.let {
                        cartDao.insertCart(it)
                    }
                }
                cartDao.getCarts()
            } else {
                emptyList()
            }
        }
        return flowOf(carts)
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

    override suspend fun getCartAmount(): Flow<Double> {
        return cartDao.getCart(dataStoreManager.currentUserId.first()).flatMapLatest { cart ->
            val cartAmount = cart?.products
                ?.filter { it.isSelected }
                ?.sumOf {
                    it.price?.times(it.quantity) ?: 0.0
                } ?: 0.0
            flowOf(cartAmount)
        }
    }

    override suspend fun createCart(cart: Cart): Cart? =
        apiService.createCart(cart)?.toCart()


    override suspend fun updateCart(cart: Cart): Int =
        cartDao.updateCart(cart.id, cart.products)

}