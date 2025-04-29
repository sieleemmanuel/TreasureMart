package com.sielehub.treasuremart.domain.use_case.product.wishlist

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.data.repository.StoreRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoveFromWishListUseCase(
    private val storeRepositoryImpl: StoreRepositoryImpl
) {
    operator fun invoke(productId: Int): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            emit(Resource.Success(storeRepositoryImpl.removeFromWishlist(productId)))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
        }
    }
}