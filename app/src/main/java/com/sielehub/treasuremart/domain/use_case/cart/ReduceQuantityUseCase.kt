package com.sielehub.treasuremart.domain.use_case.cart

import android.util.Log
import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.model.CartProduct
import com.sielehub.treasuremart.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class ReduceQuantityUseCase(
    private val cartRepository: CartRepository,
) {

    operator fun invoke(cartProduct: CartProduct): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            val cachedCart = cartRepository.getCart().first()
            cachedCart?.let {
                val cartProductToUpdate = cachedCart.products.find {
                    it.productId == cartProduct.productId
                }
                cartProductToUpdate?.let { product ->
                    val listToUpdate = cachedCart.products.toMutableList()
                    var quantity = cartProductToUpdate.quantity
                    quantity--
                    if (quantity == 0) {
                        listToUpdate.removeIf { it.productId == cartProduct.productId }
                    } else {
                        listToUpdate.find { it.productId == cartProduct.productId }?.quantity =
                            quantity
                    }
                    Log.d("ReduceQuantityUseCase", "updateCartProduct: $cartProductToUpdate")
                    val updatedCart = cachedCart.copy(products = listToUpdate)
                    val updateResult = cartRepository.updateCart(updatedCart) > 0
                    emit(Resource.Success(data = updateResult))
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
        }
    }

}