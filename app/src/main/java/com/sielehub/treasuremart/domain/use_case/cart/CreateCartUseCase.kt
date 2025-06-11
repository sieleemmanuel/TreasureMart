package com.sielehub.treasuremart.domain.use_case.cart

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.model.Cart
import com.sielehub.treasuremart.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CreateCartUseCase(private val cartRepository: CartRepository) {

    operator fun invoke(cart: Cart): Flow<Resource<Cart?>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(data = cartRepository.createCart(cart)))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
        }
    }

}