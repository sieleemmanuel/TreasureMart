package com.sielehub.treasuremart.domain.use_case.product

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.data.repository.StoreRepositoryImpl
import com.sielehub.treasuremart.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetProductUseCase(private val storeRepositoryImpl: StoreRepositoryImpl) {
    operator fun invoke(productId: Int): Flow<Resource<Product?>> = flow {
        try {
            emit(Resource.Loading())
            val productResult = storeRepositoryImpl.getProduct(productId)
            emit(Resource.Success(productResult))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
        }
    }
}