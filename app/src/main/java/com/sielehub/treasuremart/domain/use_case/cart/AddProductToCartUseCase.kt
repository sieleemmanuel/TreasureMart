package com.sielehub.treasuremart.domain.use_case.cart

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.data.datastore.DataStoreManager
import com.sielehub.treasuremart.domain.model.Cart
import com.sielehub.treasuremart.domain.model.CartProduct
import com.sielehub.treasuremart.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import java.time.LocalDate

class AddProductToCartUseCase(
    private val cartRepository: CartRepository,
    private val dataStoreManager: DataStoreManager,
) {

    operator fun invoke(cartProduct: CartProduct): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            val currentCart = cartRepository.getCart().first()
            val cartProducts = currentCart?.products?.toMutableList()
            var updateSuccess: Boolean = false
            if (cartProducts != null) {
                val existingProduct = cartProducts.find { it.productId == cartProduct.productId }
                if (existingProduct != null) {
                    existingProduct.quantity++
                } else {
                    cartProducts.add(cartProduct)
                }
                val updatedCart = currentCart.copy(products = cartProducts)
                updateSuccess = cartRepository.updateCart(updatedCart) > 0
            } else {
                val cart = Cart(
                    id = 0,
                    userId = dataStoreManager.currentUserId.first(),
                    products = listOf(cartProduct),
                    date = LocalDate.now().toString()
                )
                updateSuccess = cartRepository.updateCart(cart) > 0
            }
            emit(Resource.Success(data = updateSuccess))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
        }
    }

}