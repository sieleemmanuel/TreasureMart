package com.sielehub.treasuremart.domain.use_case.search

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.data.datastore.DataStoreManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ClearSearchHistoryUseCase(private val dataStoreManager: DataStoreManager) {
    operator fun invoke(): Flow<Resource<Boolean>> = flow {
        try {
            dataStoreManager.setSearchHistory("[]")
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error occurred clearing search history"))
        }
    }
}