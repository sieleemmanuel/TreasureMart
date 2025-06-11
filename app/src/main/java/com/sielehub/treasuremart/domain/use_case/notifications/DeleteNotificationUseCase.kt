package com.sielehub.treasuremart.domain.use_case.notifications

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteNotificationUseCase(
    private val notificationRepository: NotificationRepository
) {
    operator fun invoke(notificationId: Long): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Success(notificationRepository.deleteNotification(notificationId)))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
        }
    }

}