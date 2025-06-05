package com.sielehub.treasuremart.data.repository

import com.sielehub.treasuremart.data.local.database.StoreDao
import com.sielehub.treasuremart.domain.model.Notification
import com.sielehub.treasuremart.domain.model.Product
import com.sielehub.treasuremart.domain.network.ApiService
import com.sielehub.treasuremart.domain.repository.StoreRepository

class StoreRepositoryImpl(
    private val apiService: ApiService,
    private val dao: StoreDao
) : StoreRepository {

    override suspend fun getProducts(): List<Product> =
        apiService.getProducts().map { it.toProduct() }

    override suspend fun getProductsByCategory(category: String): List<Product> =
        apiService.getProductsByCategory(category).map { it.toProduct() }

    override suspend fun getSuperDealsProducts(): List<Product> {
        val products = apiService.getProducts().map { it.toProduct() }
        val superDealsProducts = mutableListOf<Product>()
        products.groupBy { it.category }
            .forEach { (_, products) ->
                val categoryProduct = products.take(2)
                superDealsProducts.addAll(categoryProduct)
            }
        return superDealsProducts.distinct().shuffled()
    }

    override suspend fun getBestPickProducts(): List<Product> {
        val products = apiService.getProducts().map { it.toProduct() }
        val bestPickProducts = mutableListOf<Product>()
        products.groupBy { it.category }
            .forEach { (_, products) ->
                val categoryProduct = products.take(3)
                bestPickProducts.addAll(categoryProduct)
            }
        return bestPickProducts.distinct().shuffled()
    }

    override suspend fun getProduct(id: Int): Product? =
        apiService.getProduct(id)?.toProduct()


    override suspend fun getCategories(): List<String> = apiService.getCategories()

    override suspend fun getSearchedProducts(query: String): List<Product> {
        val products = apiService.getProducts().map { it.toProduct() }
        return products.filter {
            it.title.contains(query, true) || it.description.contains(query, true)
        }
    }

    override suspend fun getNotifications(): List<Notification> =
        listOf(
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