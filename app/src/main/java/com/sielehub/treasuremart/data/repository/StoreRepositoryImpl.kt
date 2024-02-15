package com.sielehub.treasuremart.data.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.data.remote.StoreService
import com.sielehub.treasuremart.domain.model.Cart
import com.sielehub.treasuremart.domain.model.Product
import com.sielehub.treasuremart.domain.model.User
import com.sielehub.treasuremart.domain.repository.StoreRepository
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class StoreRepositoryImpl(private val storeService: StoreService) : StoreRepository {

    override fun getProducts(): Flow<Resource<List<Product>>> = flow {
        try {
            emit(Resource.Loading())
            val products = storeService.getProducts().map { it.toProduct() }
            emit(Resource.Success(data = products))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Un unknown error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("No internet connection, please check and try again"))
        }
    }

    override fun getProductsByCategory(category: String): Flow<Resource<List<Product>>> = flow {
        try {
            emit(Resource.Loading())
            val products = storeService.getProductsByCategory(category).map { it.toProduct() }
            emit(Resource.Success(data = products))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Un unknown error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("No internet connection, please check and try again"))
        }
    }

    override fun getProduct(id: Int): Flow<Resource<Product?>> = flow {
        try {
            emit(Resource.Loading())
            val product = storeService.getProduct(id)?.toProduct()
            emit(Resource.Success(data = product))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Un unknown error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("No internet connection, please check and try again"))
        }
    }


    override fun getCategories(): Flow<Resource<List<String>>> = flow {
        try {
            emit(Resource.Loading())
            val categories = storeService.getCategories()
            emit(Resource.Success(data = categories))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Un unknown error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("No internet connection, please check and try again"))
        }
    }

    override fun getUser(userId: Int): Flow<Resource<User?>> = flow {
        try {
            emit(Resource.Loading())
            val userResponse = storeService.getUser(userId)?.toUser()
            emit(Resource.Success(data = userResponse))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Un unknown error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("No internet connection, please check and try again"))
        }
    }

    override fun authenticateUser(user: User): Flow<Resource<String?>> = flow {
        try {
            emit(Resource.Loading())
            val token = storeService.authenticateUser(user)
            emit(Resource.Success(data = token))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Un unknown error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("No internet connection, please check and try again"))
        }
    }

    override suspend fun createUser(user: User): Resource<User?> {
        return try {
            Resource.Success(storeService.createUser(user)?.toUser())
        } catch (e: HttpException) {
            Resource.Error(e.localizedMessage ?: "Un unknown error occurred")
        } catch (e: IOException) {
            Resource.Error("No internet connection, please check and try again")
        }
    }

    override suspend fun updateUser(user: User): Resource<User?> {
        return try {
            Resource.Success(storeService.updateUser(user)?.toUser())
        } catch (e: HttpException) {
            Resource.Error(e.localizedMessage ?: "Un unknown error occurred")
        } catch (e: IOException) {
            Resource.Error("No internet connection, please check and try again")
        }
    }

    override fun getCarts(): Flow<Resource<List<Cart>>> = flow {
        try {
            emit(Resource.Loading())
            val carts = storeService.getCarts().map { it.toCart() }
            emit(Resource.Success(data = carts))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Un unknown error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("No internet connection, please check and try again"))
        }
    }

    override fun getCart(id: Int): Flow<Resource<Cart?>> = flow {
        try {
            emit(Resource.Loading())
            val cart = storeService.getCart(id)?.toCart()
            emit(Resource.Success(data = cart))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Un unknown error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("No internet connection, please check and try again"))
        }
    }

    override suspend fun createCart(cart: Cart): Resource<Cart?> {
        return try {
            Resource.Success(storeService.createCart(cart)?.toCart())
        } catch (e: HttpException) {
            Resource.Error(e.localizedMessage ?: "Un unknown error occurred")
        } catch (e: IOException) {
            Resource.Error("No internet connection, please check and try again")
        }
    }

    override suspend fun updateCart(cart: Cart): Resource<Cart?> {
        return try {
            Resource.Success(storeService.updateCart(cart)?.toCart())
        } catch (e: HttpException) {
            Resource.Error(e.localizedMessage ?: "Un unknown error occurred")
        } catch (e: IOException) {
            Resource.Error("No internet connection, please check and try again")
        }
    }
}