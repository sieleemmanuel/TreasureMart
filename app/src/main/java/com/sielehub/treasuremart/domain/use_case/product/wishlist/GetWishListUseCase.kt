package com.sielehub.treasuremart.domain.use_case.product.wishlist

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.model.WishProduct
import com.sielehub.treasuremart.domain.repository.WishRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWishListUseCase(
    private val wishRepository: WishRepository
) {
    operator fun invoke(): Flow<Resource<List<WishProduct>>> = flow {
        try {
            emit(Resource.Loading())
            emit(Resource.Success(wishRepository.getWishlist()))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
        }
    }
}