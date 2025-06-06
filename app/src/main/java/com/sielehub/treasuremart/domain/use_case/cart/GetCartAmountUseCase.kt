package com.sielehub.treasuremart.domain.use_case.cart

import android.util.Log
import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCartAmountUseCase(
    private val cartRepository: CartRepository
) {
    operator fun invoke(): Flow<Resource<Double>> = flow {
        emit(Resource.Loading())
        try {
            cartRepository.getCartAmount().collect { cartAmount ->
                Log.d("GetCartAmountUseCase", "CartAmount: $cartAmount")
                emit(Resource.Success(data = cartAmount))
            }
        } catch (e: Exception) {
            Log.d("GetCartAmountUseCase", "Error: ${e.message}")
            emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
        }
    }

}