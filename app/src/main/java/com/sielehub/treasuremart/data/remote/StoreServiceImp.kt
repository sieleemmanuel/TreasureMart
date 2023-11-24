package com.sielehub.treasuremart.data.remote

import android.util.Log
import com.sielehub.treasuremart.core.Constants
import com.sielehub.treasuremart.data.remote.dto.CartDto
import com.sielehub.treasuremart.data.remote.dto.ProductDto
import com.sielehub.treasuremart.data.remote.dto.UserDto
import com.sielehub.treasuremart.domain.model.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType

class StoreServiceImp(private val client: HttpClient): StoreService {
    override suspend fun getProducts(): List<ProductDto> {
        return try {
            val response = client.get (Constants.HttpRoutes.PRODUCTS_ENDPOINT).body<List<ProductDto>>()
            Log.d("StoreService", "getProducts: $response")
            response
        }catch (e: ClientRequestException){
            Log.d("StoreService", "Error: ${e.response.status.description}")
            emptyList()
        }catch (e: ServerResponseException){
            Log.d("StoreService", "Error: ${e.response.status.description}")
            emptyList()
        }catch (e: Exception){
            Log.d("StoreService", "Error: ${e.message}")
            emptyList()
        }
    }
    override suspend fun getProduct(id: Int): ProductDto? {
        return try {
            val response = client.get (Constants.HttpRoutes.PRODUCT_ENDPOINT){
                url { appendPathSegments("$id") }
            }.body<ProductDto>()
            response
        }catch (e: ServerResponseException){
            Log.d("StoreService", "Error: ${e.response.status.description}")
            null
        }catch (e: Exception){
            Log.d("StoreService", "Error: ${e.message}")
            null
        }
    }

    override suspend fun getUser(userId: Int): UserDto? {
        return try {
            val response = client.get (Constants.HttpRoutes.USERS_ENDPOINT){
                url { appendPathSegments("$userId") }
            }.body<UserDto>()
            response
        }catch (e: ServerResponseException){
            Log.d("StoreService", "Error: ${e.response.status.description}")
            null
        }catch (e: Exception){
            Log.d("StoreService", "Error: ${e.message}")
            null
        }
    }

    override suspend fun createUser(user: User): UserDto? {
        return try {
            client.post(Constants.HttpRoutes.CREATE_USER_ENDPOINT){
                contentType(ContentType.Application.Json)
                setBody(user)
            }.body<UserDto>()
        }catch (e: ClientRequestException){
            Log.d("StoreService", "Error: ${e.response.status.description}")
           null
        }catch (e: ServerResponseException){
            Log.d("StoreService", "Error: ${e.response.status.description}")
            null
        }catch (e: Exception){
            Log.d("StoreService", "Error: ${e.message}")
            null
        }
    }

    override suspend fun sortProducts(isASC: Boolean): List<ProductDto> {
       return try {
           val response = client.get (Constants.HttpRoutes.PRODUCTS_ENDPOINT){
               url {
                   parameters.append("sort", if (isASC) "asc" else "desc")
               }
           }.body<List<ProductDto>>()
           response
       }catch (e:RedirectResponseException){
           emptyList()
       }
    }

    override suspend fun getCategories(): List<String> {
        return try {
             client.get(Constants.HttpRoutes.CATEGORIES_ENDPOINT)
                .body()
        }catch (e: ClientRequestException){
            Log.d("StoreService", "Error: ${e.response.status.description}")
            emptyList()
        }catch (e: ServerResponseException){
            Log.d("StoreService", "Error: ${e.response.status.description}")
            emptyList()
        }catch (e: Exception){
            Log.d("StoreService", "Error: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getProductsByCategory(category: String): List<ProductDto> {
        return try {
            val productResponse = client.get (Constants.HttpRoutes.CATEGORY_PRODUCTS_ENDPOINT){
                url { appendPathSegments(category) }
            }.body<List<ProductDto>>()
            productResponse
        } catch (e: ClientRequestException){
            Log.d("StoreService", "Error: ${e.response.status.description}")
            emptyList()
        }catch (e: ServerResponseException){
            Log.d("StoreService", "Error: ${e.response.status.description}")
            emptyList()
        }catch (e: Exception){
            Log.d("StoreService", "Error: ${e.message}")
            emptyList()
        }
    }

    override suspend fun authenticateUser(user: User): String? {
        return try {
            val token = client.post (Constants.HttpRoutes.AUTH_ENDPOINT){
                contentType(ContentType.Application.Json)
                setBody(user)
            }.body<String?>()
            token
        } catch (e: ClientRequestException){
            Log.d("StoreService", "Error: ${e.response.status.description}")
            null
        }catch (e: ServerResponseException){
            Log.d("StoreService", "Error: ${e.response.status.description}")
            null
        }catch (e: Exception){
            Log.d("StoreService", "Error: ${e.message}")
            null
        }
    }

    override suspend fun updateUser(user: User): UserDto? {
        return try {
            val updateResponse = client.post (Constants.HttpRoutes.USERS_ENDPOINT){
                url { appendPathSegments(user.id.toString()) }
                contentType(ContentType.Application.Json)
                setBody(user)
            }.body<UserDto?>()
            updateResponse
        } catch (e: ClientRequestException){
            Log.d("StoreService", "Error: ${e.response.status.description}")
            null
        }catch (e: ServerResponseException){
            Log.d("StoreService", "Error: ${e.response.status.description}")
            null
        }catch (e: Exception){
            Log.d("StoreService", "Error: ${e.message}")
            null
        }
    }

    override suspend fun getCarts(): List<CartDto> {
        return try {
            client.get(Constants.HttpRoutes.CARTS_ENDPOINT)
                .body()
        }catch (e: ClientRequestException){
            Log.d("StoreService", "Error: ${e.response.status.description}")
            emptyList()
        }catch (e: ServerResponseException){
            Log.d("StoreService", "Error: ${e.response.status.description}")
            emptyList()
        }catch (e: Exception){
            Log.d("StoreService", "Error: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getCart(id: Int): CartDto? {
        return try {
            val cartResponse = client.get (Constants.HttpRoutes.CARTS_ENDPOINT){
                url { appendPathSegments("$id") }
            }.body<CartDto>()
            cartResponse
        }catch (e: ServerResponseException){
            Log.d("StoreServiceImp", "Error: ${e.response.status.description}")
            null
        }catch (e: Exception){
            Log.d("StoreServiceImp", "Error: ${e.message}")
            null
        }
    }
}