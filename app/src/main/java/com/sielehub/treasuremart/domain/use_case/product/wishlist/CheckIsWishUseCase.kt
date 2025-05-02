package com.sielehub.treasuremart.domain.use_case.product.wishlist

import com.sielehub.treasuremart.data.repository.StoreRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CheckIsWishUseCase(
    private val storeRepositoryImpl: StoreRepositoryImpl
) {
    operator fun invoke(productId: Int): Flow<Boolean> = flow {
        try {
            emit(storeRepositoryImpl.checkIsWish(productId))
        } catch (e: Exception) {
            emit(false)
        }
    }
}