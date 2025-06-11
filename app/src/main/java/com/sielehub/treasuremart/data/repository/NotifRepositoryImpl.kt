package com.sielehub.treasuremart.data.repository

import com.sielehub.treasuremart.data.local.database.NotificationsDao
import com.sielehub.treasuremart.domain.model.Notification
import com.sielehub.treasuremart.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow

class NotificationRepositoryImpl(
    private val notificationsDao: NotificationsDao
) : NotificationRepository {
    override suspend fun insertNotification(notification: Notification) {
        notificationsDao.insertNotification(notification)
    }

    override suspend fun getNotifications(): Flow<List<Notification>> {
        return notificationsDao.getNotifications()
    }

    override suspend fun deleteNotification(id: Long) {
        notificationsDao.deleteNotification(id)
    }

}