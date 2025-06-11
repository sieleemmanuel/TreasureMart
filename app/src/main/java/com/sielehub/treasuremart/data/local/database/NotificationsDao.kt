package com.sielehub.treasuremart.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sielehub.treasuremart.domain.model.Notification
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationsDao {
    @Insert(Notification::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: Notification)

    @Query("SELECT * FROM notifications_table")
    fun getNotifications(): Flow<List<Notification>>

    @Query("DELETE FROM notifications_table WHERE id = :id")
    suspend fun deleteNotification(id: Long)

}