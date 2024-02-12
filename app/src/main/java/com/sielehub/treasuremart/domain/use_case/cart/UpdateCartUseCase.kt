package com.sielehub.treasuremart.domain.use_case.cart

import com.sielehub.treasuremart.domain.model.Cart
import com.sielehub.treasuremart.domain.repository.StoreRepository

class UpdateCartUseCase(private val storeRepository: StoreRepository) {

    suspend operator fun invoke(cart: Cart) = storeRepository.updateCart(cart)
}