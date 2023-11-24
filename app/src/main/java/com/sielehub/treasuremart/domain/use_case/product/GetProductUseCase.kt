package com.sielehub.treasuremart.domain.use_case.product

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.model.Product
import com.sielehub.treasuremart.domain.repository.StoreRepository
import kotlinx.coroutines.flow.Flow

class GetProductUseCase(private val storeRepository: StoreRepository) {
    operator fun invoke(productId: Int): Flow<Resource<Product?>>  = storeRepository.getProduct(productId)

}