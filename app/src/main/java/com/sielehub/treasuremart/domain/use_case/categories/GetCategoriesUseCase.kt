package com.sielehub.treasuremart.domain.use_case.categories

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.data.repository.StoreRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCategoriesUseCase(private val storeRepository: StoreRepositoryImpl) {

    operator fun invoke(): Flow<Resource<List<String>>> = flow {
        try {
            emit(Resource.Loading())
            emit(Resource.Success(data = storeRepository.getCategories()))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
        }
    }
}