package com.sielehub.treasuremart.data.repository

import android.util.Log
import com.sielehub.treasuremart.data.datastore.DataStoreManager
import com.sielehub.treasuremart.data.local.database.CartDao
import com.sielehub.treasuremart.domain.model.Cart
import com.sielehub.treasuremart.domain.model.CartProduct
import com.sielehub.treasuremart.domain.network.ApiService
import com.sielehub.treasuremart.domain.repository.CartRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate

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

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getCart(): Flow<Cart?> {
        return dataStoreManager.currentUserId.flatMapLatest { userId ->
            if (userId > 0) {
                cartDao.getCart(userId)
            } else {
                flowOf(null)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
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


    override suspend fun updateCart(cartProduct: CartProduct): Boolean {
        val currentCart = cartDao.getCart(dataStoreManager.currentUserId.first()).first()
        val cartProducts = currentCart?.products?.toMutableList()
        var updatedCart: Cart? = null
        var updateSuccess = false
        currentCart?.let {
            if (cartProducts != null) {
                val existingProduct =
                    cartProducts.find { it.productId == cartProduct.productId }
                if (existingProduct != null) {
                    existingProduct.quantity++
                } else {
                    cartProducts.add(cartProduct)
                }
                updatedCart = currentCart.copy(products = cartProducts)
                updateSuccess = cartDao.updateCart(updatedCart.id, updatedCart.products) > 0
            } else {
                updatedCart = currentCart.copy(
                    products = listOf(cartProduct)
                )
                updateSuccess = cartDao.updateCart(updatedCart.id, updatedCart.products) > 0
            }
        } ?: run {
            val cart = Cart(
                id = 1,
                userId = dataStoreManager.currentUserId.first(),
                products = listOf(cartProduct),
                date = LocalDate.now().toString()
            )
            updateSuccess = cartDao.insertCart(cart) > 0

        }
        return updateSuccess
    }

    override suspend fun checkCartProduct(cartProduct: CartProduct): Boolean {
        val cachedCart = cartDao.getCart(dataStoreManager.currentUserId.first()).first()
        val listToUpdate = cachedCart?.products?.toMutableList()
        listToUpdate?.find { it.productId == cartProduct.productId }?.isSelected =
            !cartProduct.isSelected
        val updatedCart = cachedCart?.copy(products = listToUpdate ?: emptyList())
        return updatedCart?.let {
            cartDao.updateCart(updatedCart.id, updatedCart.products) > 0
        } == true
    }

    override suspend fun updateCartProductQuantity(
        cartProduct: CartProduct,
        isIncrease: Boolean
    ): Boolean {
        val cachedCart = cartDao.getCart(dataStoreManager.currentUserId.first()).first()
        return cachedCart?.let {
            val cartProductToUpdate = cachedCart.products.find {
                it.productId == cartProduct.productId
            }
            cartProductToUpdate?.let { product ->
                val listToUpdate = cachedCart.products.toMutableList()
                var quantity = cartProductToUpdate.quantity
                if (isIncrease) {
                    quantity++
                    listToUpdate.find { it.productId == cartProduct.productId }?.quantity =
                        quantity
                } else {
                    quantity--
                    if (quantity == 0) {
                        listToUpdate.removeIf { it.productId == cartProduct.productId }
                    } else {
                        listToUpdate.find { it.productId == cartProduct.productId }?.quantity =
                            quantity
                    }
                }
                val updatedCart = cachedCart.copy(products = listToUpdate)
                updatedCart.let { cartDao.updateCart(cachedCart.id, listToUpdate) } > 0
            }
        } == true
    }

}