package com.sielehub.treasuremart.domain.use_case.cart

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.model.CartProduct
import com.sielehub.treasuremart.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddProductToCartUseCase(
    private val cartRepository: CartRepository,
) {

    operator fun invoke(cartProduct: CartProduct): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(data = cartRepository.updateCart(cartProduct)))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
        }
    }

}