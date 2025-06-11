package com.sielehub.treasuremart.domain.use_case.notifications

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.model.Notification
import com.sielehub.treasuremart.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InsertNotificationUseCase(private val notificationRepository: NotificationRepository) {

    operator fun invoke(notification: Notification): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(data = notificationRepository.insertNotification(notification)))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
        }
    }

}