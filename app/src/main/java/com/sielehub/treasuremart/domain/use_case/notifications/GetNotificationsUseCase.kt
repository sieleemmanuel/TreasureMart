package com.sielehub.treasuremart.domain.use_case.notifications

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.model.Notification
import com.sielehub.treasuremart.domain.repository.StoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetNotificationsUseCase(private val storeRepository: StoreRepository) {
    operator fun invoke(): Flow<Resource<List<Notification>>> = flow {
        try {
            emit(Resource.Loading())
            val notificationsResult = storeRepository.getNotifications()
            emit(Resource.Success(notificationsResult))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
        }

    }
}