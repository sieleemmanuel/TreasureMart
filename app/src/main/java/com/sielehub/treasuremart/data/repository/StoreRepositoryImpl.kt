package com.sielehub.treasuremart.data.repository

import com.sielehub.treasuremart.data.remote.StoreService
import com.sielehub.treasuremart.domain.model.Cart
import com.sielehub.treasuremart.domain.model.Notification
import com.sielehub.treasuremart.domain.model.Product
import com.sielehub.treasuremart.domain.repository.StoreRepository

class StoreRepositoryImpl(private val storeService: StoreService) : StoreRepository {

    override suspend fun getProducts(): List<Product> {
        return storeService.getProducts().map { it.toProduct() }
    }

    override suspend fun getProductsByCategory(category: String): List<Product> {
        return storeService.getProductsByCategory(category).map { it.toProduct() }
    }

    override suspend fun getProduct(id: Int): Product? {
        return storeService.getProduct(id)?.toProduct()
    }

    override suspend fun getCategories(): List<String> {
        return storeService.getCategories()
    }

    override suspend fun getCarts(): List<Cart> {
        return storeService.getCarts().map { it.toCart() }
    }

    override suspend fun getCart(id: Int): Cart? =
        storeService.getCart(id)?.toCart()

    override suspend fun createCart(cart: Cart): Cart? {
        return storeService.createCart(cart)?.toCart()
    }

    override suspend fun updateCart(cart: Cart): Cart? {
        return storeService.updateCart(cart)?.toCart()
    }

    override suspend fun getNotifications(): List<Notification> {
        return listOf(
            Notification(
                notifId = Long.MIN_VALUE,
                message = "Order ahs been placed successfully and your product will be shipped as soon as possible"
            ),
            Notification(
                notifId = Long.MIN_VALUE,
                message = "Order ahs been placed successfully and your product will be shipped as soon as possible"
            ),
            Notification(
                notifId = Long.MIN_VALUE,
                message = "Order ahs been placed successfully and your product will be shipped as soon as possible"
            ),
        )
    }

}