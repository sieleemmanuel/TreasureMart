package com.sielehub.treasuremart.domain.repository

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.model.Cart
import com.sielehub.treasuremart.domain.model.Product
import com.sielehub.treasuremart.domain.model.User
import kotlinx.coroutines.flow.Flow

interface StoreRepository {

    fun getProducts(): Flow<Resource<List<Product>>>

    fun getProductsByCategory(category: String): Flow<Resource<List<Product>>>

    fun getProduct(id: Int): Flow<Resource<Product?>>

    fun getCategories(): Flow<Resource<List<String>>>

    fun getUser(userId: Int): Flow<Resource<User?>>

    fun authenticateUser(user: User): Flow<Resource<String?>>

    suspend fun createUser(user: User): Resource<User?>

    suspend fun updateUser(user: User): Resource<User?>

    suspend fun createCart(cart: Cart): Resource<Cart?>

    suspend fun updateCart(cart: Cart): Resource<Cart?>

    fun getCarts(): Flow<Resource<List<Cart>>>

    fun getCart(id: Int): Flow<Resource<Cart?>>

}