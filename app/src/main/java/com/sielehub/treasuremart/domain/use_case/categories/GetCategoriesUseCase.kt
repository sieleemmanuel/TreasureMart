package com.sielehub.treasuremart.domain.use_case.categories

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.repository.StoreRepository
import kotlinx.coroutines.flow.Flow

class GetCategoriesUseCase(private val storeRepository: StoreRepository) {

    operator fun invoke(): Flow<Resource<List<String>>> = storeRepository.getCategories()
}