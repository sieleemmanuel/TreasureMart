package com.sielehub.treasuremart.data.repository

import com.sielehub.treasuremart.data.local.database.NotificationsDao
import com.sielehub.treasuremart.domain.model.Notification
import com.sielehub.treasuremart.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow

class NotificationRepositoryImpl(
    private val notificationsDao: NotificationsDao
) : NotificationRepository {

    override suspend fun insertNotification(notification: Notification): Boolean {
        return notificationsDao.insertNotification(notification) > 0
    }

    override suspend fun getNotifications(): Flow<List<Notification>> {
        return notificationsDao.getNotifications()
    }

    override suspend fun markNotificationAsRead(isRead: Boolean, id: Long): Boolean {
        return notificationsDao.markNotificationAsRead(isRead, id) > 1
    }

    override suspend fun deleteNotification(id: Long): Boolean {
        return notificationsDao.deleteNotification(id) > 0
    }

}