package com.sielehub.treasuremart.domain.network

import com.sielehub.treasuremart.data.remote.dto.CartDto
import com.sielehub.treasuremart.data.remote.dto.ProductDto
import com.sielehub.treasuremart.data.remote.dto.UserDto
import com.sielehub.treasuremart.domain.model.Cart
import com.sielehub.treasuremart.domain.model.LoginRequest
import com.sielehub.treasuremart.domain.model.SignupRequest
import com.sielehub.treasuremart.domain.model.SignupResponse
import com.sielehub.treasuremart.domain.model.Token
import com.sielehub.treasuremart.domain.model.User

interface ApiService {

    suspend fun getProducts(): List<ProductDto>

    suspend fun getProduct(id: Int): ProductDto?

    suspend fun getProductsByCategory(category: String): List<ProductDto>

    suspend fun sortProducts(isASC: Boolean): List<ProductDto>

    suspend fun getCategories(): List<String>

    suspend fun getUsers(): List<UserDto>

    suspend fun getUser(userId: Int): UserDto?

    suspend fun createUser(signupRequest: SignupRequest): SignupResponse

    suspend fun updateUser(user: User): UserDto?

    suspend fun authenticateUser(loginRequest: LoginRequest): Token?

    suspend fun getCarts(): List<CartDto>

    suspend fun getCart(id: Int): CartDto?

    suspend fun createCart(newCart: Cart): CartDto?

    suspend fun updateCart(cart: Cart): CartDto?
}