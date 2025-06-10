package com.sielehub.treasuremart.domain.use_case.wishlist

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.model.WishProduct
import com.sielehub.treasuremart.domain.repository.WishRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddToWishListUseCase(
    private val wishRepository: WishRepository
) {
    operator fun invoke(wishProduct: WishProduct): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            emit(Resource.Success(wishRepository.addToWishlist(wishProduct)))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
        }
    }
}