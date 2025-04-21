package com.sielehub.treasuremart.domain.use_case.product

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.data.repository.StoreRepositoryImpl
import com.sielehub.treasuremart.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetProductsByCategoryUseCase(private val storeRepositoryImpl: StoreRepositoryImpl) {

    operator fun invoke(category: String): Flow<Resource<List<Product>>> = flow {
        try {
            emit(Resource.Loading())
            val categoryProductsResult = storeRepositoryImpl.getProductsByCategory(category)
            emit(Resource.Success(categoryProductsResult))
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    e.localizedMessage ?: "An unknown error occurred displaying products"
                )
            )
        }
    }
}