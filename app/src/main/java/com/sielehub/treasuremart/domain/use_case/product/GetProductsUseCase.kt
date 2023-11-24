package com.sielehub.treasuremart.domain.use_case.product

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.model.Product
import com.sielehub.treasuremart.domain.repository.StoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetProductsUseCase(private val storeRepository: StoreRepository) {

    operator fun invoke(): Flow<Resource<List<Product>>> = storeRepository.getProducts()
}