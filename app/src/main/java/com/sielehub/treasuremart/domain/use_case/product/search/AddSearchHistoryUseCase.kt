package com.sielehub.treasuremart.domain.use_case.product.search

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.data.datastore.DataStoreManager
import com.sielehub.treasuremart.presentation.util.jsonToModel
import com.sielehub.treasuremart.presentation.util.modelToJsonArray
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class AddSearchHistoryUseCase(private val dataStoreManager: DataStoreManager) {
    operator fun invoke(query: String): Flow<Resource<Boolean>> = flow {
        try {
            val currentHistory =
                dataStoreManager.searchHistory.first().jsonToModel<MutableList<String>>()
            if (!currentHistory.contains(query)) {
                currentHistory.add(query)
                dataStoreManager.setSearchHistory(currentHistory.modelToJsonArray())
                emit(Resource.Success(true))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error adding saving search"))
        }
    }
}
