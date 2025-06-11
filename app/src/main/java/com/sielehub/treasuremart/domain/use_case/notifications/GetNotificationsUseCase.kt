package com.sielehub.treasuremart.domain.use_case.notifications

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.model.Notification
import com.sielehub.treasuremart.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetNotificationsUseCase(private val notificationRepository: NotificationRepository) {
    operator fun invoke(): Flow<Resource<List<Notification>>> = flow {
        emit(Resource.Loading())
        try {
            notificationRepository.getNotifications().collect { notifications ->
                emit(Resource.Success(notifications))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
        }
    }
}