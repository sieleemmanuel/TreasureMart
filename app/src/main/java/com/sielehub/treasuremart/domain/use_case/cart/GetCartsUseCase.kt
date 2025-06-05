package com.sielehub.treasuremart.domain.use_case.cart

import android.util.Log
import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.model.Cart
import com.sielehub.treasuremart.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCartsUseCase(private val cartRepository: CartRepository) {

    operator fun invoke(): Flow<Resource<List<Cart>>> = flow {
        try {
            emit(Resource.Loading())
            val carts = cartRepository.getCarts()
            Log.d(GetCartsUseCase::class.simpleName, "invoke: $carts")
            emit(Resource.Success(data = carts))
        } catch (e: Exception) {
            Log.d(GetCartsUseCase::class.simpleName, "invoke: ${e.localizedMessage}")
            emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
        }
    }

}