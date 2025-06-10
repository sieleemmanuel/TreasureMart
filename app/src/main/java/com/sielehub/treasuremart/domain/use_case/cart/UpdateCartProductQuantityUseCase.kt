package com.sielehub.treasuremart.domain.use_case.cart

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.model.CartProduct
import com.sielehub.treasuremart.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateCartProductQuantityUseCase(
    private val cartRepository: CartRepository,
) {

    operator fun invoke(isIncrease: Boolean, cartProduct: CartProduct): Flow<Resource<Boolean>> =
        flow {
            emit(Resource.Loading())
            try {
                Resource.Success(
                    data = cartRepository.updateCartProductQuantity(
                        cartProduct,
                        isIncrease
                    )
                )
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
            }
        }

}