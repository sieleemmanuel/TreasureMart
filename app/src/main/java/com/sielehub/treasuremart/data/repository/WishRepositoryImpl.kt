package com.sielehub.treasuremart.data.repository

import com.sielehub.treasuremart.data.local.database.WishDao
import com.sielehub.treasuremart.domain.model.WishProduct
import com.sielehub.treasuremart.domain.repository.WishRepository
import kotlinx.coroutines.flow.Flow

class WishRepositoryImpl(
    private val dao: WishDao
) : WishRepository {

    override suspend fun getWishlist(): Flow<List<WishProduct>> {
        return dao.getWishList()
    }

    override suspend fun addToWishlist(wishProduct: WishProduct) {
        return dao.insertWishProduct(wishProduct)
    }

    override suspend fun checkIsWish(productId: Int): Boolean {
        return dao.checkIsWish(productId)
    }

    override suspend fun removeFromWishlist(productId: Int) {
        return dao.removeFromWishList(productId)
    }

}