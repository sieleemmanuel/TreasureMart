package com.sielehub.treasuremart.domain.use_case.product.wishlist

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.repository.WishRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoveFromWishListUseCase(
    private val wishRepository: WishRepository
) {
    operator fun invoke(productId: Int): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            emit(Resource.Success(wishRepository.removeFromWishlist(productId)))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
        }
    }
}