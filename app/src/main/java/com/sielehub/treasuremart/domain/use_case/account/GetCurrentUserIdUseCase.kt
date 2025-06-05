package com.sielehub.treasuremart.domain.use_case.account

import com.sielehub.treasuremart.data.datastore.DataStoreManager
import kotlinx.coroutines.flow.Flow

class GetCurrentUserIdUseCase(private val dataStoreManager: DataStoreManager) {

    operator fun invoke(): Flow<Int> = dataStoreManager.currentUserId
}