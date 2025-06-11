package com.sielehub.treasuremart.domain.repository

import com.sielehub.treasuremart.domain.model.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {

    suspend fun insertNotification(notification: Notification)

    suspend fun getNotifications(): Flow<List<Notification>>

    suspend fun deleteNotification(id: Long)

}