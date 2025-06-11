package com.sielehub.treasuremart.domain.repository

import com.sielehub.treasuremart.domain.model.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {

    suspend fun insertNotification(notification: Notification): Boolean

    suspend fun getNotifications(): Flow<List<Notification>>

    suspend fun markNotificationAsRead(isRead: Boolean, id: Long): Boolean

    suspend fun deleteNotification(id: Long): Boolean

}