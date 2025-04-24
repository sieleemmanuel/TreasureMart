package com.sielehub.treasuremart.domain.use_case.product.wishlist

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.data.repository.StoreRepositoryImpl
import com.sielehub.treasuremart.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddToWishListUseCase(
    private val storeRepositoryImpl: StoreRepositoryImpl
) {
    operator fun invoke(product: Product): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            emit(Resource.Success(storeRepositoryImpl.addToWishlist(product)))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
        }
    }
}