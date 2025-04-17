package com.sielehub.treasuremart.domain.use_case.account

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.data.datastore.DataStoreManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LogoutUseCase(private val dataStoreManager: DataStoreManager) {

    operator fun invoke(): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            delay(1000)
            dataStoreManager.setAuthToken("")
            emit(Resource.Success(data = true))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
        }
    }
}