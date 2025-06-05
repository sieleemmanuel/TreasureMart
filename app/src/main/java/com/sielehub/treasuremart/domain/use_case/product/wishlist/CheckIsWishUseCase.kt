package com.sielehub.treasuremart.domain.use_case.product.wishlist

import com.sielehub.treasuremart.domain.repository.WishRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CheckIsWishUseCase(
    private val wishRepository: WishRepository
) {
    operator fun invoke(productId: Int): Flow<Boolean> = flow {
        try {
            emit(wishRepository.checkIsWish(productId))
        } catch (e: Exception) {
            emit(false)
        }
    }
}