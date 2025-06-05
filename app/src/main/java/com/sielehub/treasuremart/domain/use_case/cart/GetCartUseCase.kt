package com.sielehub.treasuremart.domain.use_case.cart

import android.util.Log
import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.model.Cart
import com.sielehub.treasuremart.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCartUseCase(
    private val cartRepository: CartRepository,
) {

    operator fun invoke(showLoading: Boolean = true): Flow<Resource<Cart?>> = flow {
        if (showLoading) {
            emit(Resource.Loading())
        }
        try {
            cartRepository.getCart().collect { cart ->
                emit(Resource.Success(cart))
            }
        } catch (e: Exception) {
            Log.d("GetCartUseCase", "GetCartUseCase error: ${e.message}")
            emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
        }
    }

}