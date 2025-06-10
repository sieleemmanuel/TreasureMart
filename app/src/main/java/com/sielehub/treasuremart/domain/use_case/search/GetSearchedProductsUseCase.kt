package com.sielehub.treasuremart.domain.use_case.search

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.data.repository.StoreRepositoryImpl
import com.sielehub.treasuremart.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSearchedProductsUseCase(private val storeRepositoryImpl: StoreRepositoryImpl) {
    operator fun invoke(query: String): Flow<Resource<List<Product>>> = flow {
        try {
            emit(Resource.Loading())
            val searchedProducts = storeRepositoryImpl.getSearchedProducts(query)
            emit(Resource.Success(searchedProducts))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error occurred during search"))
        }
    }
}