package com.sielehub.treasuremart.data.network

import android.util.Log
import com.sielehub.treasuremart.core.Constants
import com.sielehub.treasuremart.data.remote.dto.CartDto
import com.sielehub.treasuremart.data.remote.dto.ProductDto
import com.sielehub.treasuremart.data.remote.dto.UserDto
import com.sielehub.treasuremart.domain.model.Cart
import com.sielehub.treasuremart.domain.model.LoginRequest
import com.sielehub.treasuremart.domain.model.SignupRequest
import com.sielehub.treasuremart.domain.model.SignupResponse
import com.sielehub.treasuremart.domain.model.Token
import com.sielehub.treasuremart.domain.model.User
import com.sielehub.treasuremart.domain.network.ApiService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType

class ApiServiceImp(private val client: HttpClient) : ApiService {
    override suspend fun getProducts(): List<ProductDto> {
        return client.get(Constants.HttpRoutes.PRODUCTS_ENDPOINT).body<List<ProductDto>>()
    }

    override suspend fun getProduct(id: Int): ProductDto? {
        return client.get(Constants.HttpRoutes.PRODUCT_ENDPOINT) {
            url { appendPathSegments("$id") }
        }.body<ProductDto>()
    }

    override suspend fun getUser(userId: Int): UserDto? {
        return client.get(Constants.HttpRoutes.USERS_ENDPOINT) {
            url { appendPathSegments("$userId") }
        }.body<UserDto>()
    }

    override suspend fun createUser(signupRequest: SignupRequest): SignupResponse {
        return client.post(Constants.HttpRoutes.CREATE_USER_ENDPOINT) {
            contentType(ContentType.Application.Json)
            setBody(signupRequest)
        }.body<SignupResponse>()
    }

    override suspend fun authenticateUser(loginRequest: LoginRequest): Token? {
        return client.post(Constants.HttpRoutes.AUTH_ENDPOINT) {
            contentType(ContentType.Application.Json)
            setBody(loginRequest)
        }.body<Token?>()
    }

    override suspend fun updateUser(user: User): UserDto? {
        return client.put(Constants.HttpRoutes.USERS_ENDPOINT) {
            url { appendPathSegments(user.id.toString()) }
            contentType(ContentType.Application.Json)
            setBody(user)
        }.body<UserDto?>()
    }

    override suspend fun sortProducts(isASC: Boolean): List<ProductDto> {
        return client.get(Constants.HttpRoutes.PRODUCTS_ENDPOINT) {
            url { parameters.append("sort", if (isASC) "asc" else "desc") }
        }.body<List<ProductDto>>()
    }

    override suspend fun getCategories(): List<String> {
        return client.get(Constants.HttpRoutes.CATEGORIES_ENDPOINT)
            .body()
    }

    override suspend fun getUsers(): List<UserDto> {
        return client.get(Constants.HttpRoutes.USERS_ENDPOINT).body<List<UserDto>>()
    }

    override suspend fun getProductsByCategory(category: String): List<ProductDto> {
        return client.get(Constants.HttpRoutes.CATEGORY_PRODUCTS_ENDPOINT) {
            url { appendPathSegments(category) }
        }.body<List<ProductDto>>()
    }

    override suspend fun getCarts(): List<CartDto> {
        val carts = client.get(Constants.HttpRoutes.CARTS_ENDPOINT).body<List<CartDto>>()
        carts.forEach {
            Log.d(ApiServiceImp::class.simpleName, "Cart: $it")
        }
        return carts
    }

    override suspend fun getCart(id: Int): CartDto? {
        return client.get(Constants.HttpRoutes.CARTS_ENDPOINT) {
            url { appendPathSegments("$id") }
        }.body<CartDto>()
    }

    override suspend fun createCart(newCart: Cart): CartDto? {
        return client.post(Constants.HttpRoutes.CARTS_ENDPOINT) {
            url { appendPathSegments(newCart.id.toString()) }
            contentType(ContentType.Application.Json)
            setBody(newCart)
        }.body<CartDto?>()
    }

    override suspend fun updateCart(cart: Cart): CartDto? {
        return client.post(Constants.HttpRoutes.CARTS_ENDPOINT) {
            url { appendPathSegments(cart.id.toString()) }
            contentType(ContentType.Application.Json)
            setBody(cart)
        }.body<CartDto?>()
    }
}