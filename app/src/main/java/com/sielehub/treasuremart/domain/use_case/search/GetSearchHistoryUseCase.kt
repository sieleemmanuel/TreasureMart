package com.sielehub.treasuremart.domain.use_case.search

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.data.datastore.DataStoreManager
import com.sielehub.treasuremart.presentation.util.jsonToModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSearchHistoryUseCase(private val dataStoreManager: DataStoreManager) {
    operator fun invoke(): Flow<Resource<List<String>>> = flow {
        try {
            dataStoreManager.searchHistory.collect { historyString ->
                emit(Resource.Success(historyString.jsonToModel<MutableList<String>>()))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error getting search history"))
        }
    }
}
