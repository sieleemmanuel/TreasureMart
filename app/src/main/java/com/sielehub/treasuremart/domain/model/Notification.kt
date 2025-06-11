package com.sielehub.treasuremart.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications_table")
data class Notification(
    @PrimaryKey(autoGenerate = false)
    val id: Long = System.currentTimeMillis(),
    val title: String = "",
    val message: String = "",
    val date: String = "",
    val orderId: Long? = 0L,
    var read: Boolean = false
)
