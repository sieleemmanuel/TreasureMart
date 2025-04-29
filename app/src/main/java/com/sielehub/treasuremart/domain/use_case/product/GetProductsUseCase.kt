package com.sielehub.treasuremart.domain.use_case.product

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.data.repository.StoreRepositoryImpl
import com.sielehub.treasuremart.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetProductsUseCase(private val storeRepositoryImpl: StoreRepositoryImpl) {

    operator fun invoke(productsQuery: String? = null): Flow<Resource<List<Product>>> = flow {
        try {
            emit(Resource.Loading())
            val productsResult = storeRepositoryImpl.getProducts()
            productsQuery?.let {
                val filteredProducts = productsResult.filter { product ->
                    product.category.contains(productsQuery, ignoreCase = true)
                            || product.title.contains(productsQuery, ignoreCase = true)
                            || product.description.contains(productsQuery, ignoreCase = true)
                }
                emit(Resource.Success(filteredProducts))
            } ?: emit(Resource.Success(productsResult))

        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
        }
    }
}