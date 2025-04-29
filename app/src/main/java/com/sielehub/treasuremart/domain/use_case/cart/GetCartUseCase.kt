package com.sielehub.treasuremart.domain.use_case.cart

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.model.Cart
import com.sielehub.treasuremart.domain.repository.StoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCartUseCase(private val storeRepository: StoreRepository) {

    operator fun invoke(cartId: Int): Flow<Resource<Cart?>> = flow {
        try {
            emit(Resource.Loading())
            emit(Resource.Success(data = storeRepository.getCart(cartId)))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
        }
    }

}