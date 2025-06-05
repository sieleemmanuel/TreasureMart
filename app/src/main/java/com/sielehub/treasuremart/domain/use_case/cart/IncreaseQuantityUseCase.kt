package com.sielehub.treasuremart.domain.use_case.cart

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.model.Cart
import com.sielehub.treasuremart.domain.model.CartProduct
import com.sielehub.treasuremart.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class IncreaseQuantityUseCase(
    private val cartRepository: CartRepository,
) {

    operator fun invoke(cartProduct: CartProduct): Flow<Resource<Cart?>> = flow {
        try {
            emit(Resource.Loading())
            val cachedCart = cartRepository.getCart().first()
            val cartProductToUpdate =
                cachedCart?.products?.find { it.productId == cartProduct.productId }
            val updatedCartProduct =
                cartProductToUpdate?.copy(quantity = cartProductToUpdate.quantity + 1)
            val listToUpdate = cachedCart?.products?.toMutableList()?.apply {
                firstOrNull { it.productId == cartProduct.productId }?.quantity =
                    updatedCartProduct?.quantity ?: 1
            }
            val updatedCart = cachedCart?.copy(products = listToUpdate ?: emptyList())
            val result = updatedCart?.let { cartRepository.updateCart(it) }
            Resource.Success(data = result)
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
        }
    }

}