package com.sielehub.treasuremart.domain.use_case.cart

import com.sielehub.treasuremart.domain.repository.StoreRepository

class GetCartUseCase(private val storeRepository: StoreRepository) {

    operator fun invoke(cartId: Int) = storeRepository.getCart(cartId)

}