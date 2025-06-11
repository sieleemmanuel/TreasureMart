package com.sielehub.treasuremart.domain.use_case.notifications

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateReadStatusUseCase(private val notificationRepository: NotificationRepository) {
    operator fun invoke(isRead: Boolean, id: Long): Flow<Resource<Boolean>> = flow {
        try {
            val updateResult = notificationRepository.markNotificationAsRead(isRead, id)
            emit(Resource.Success(updateResult))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
        }
    }
}