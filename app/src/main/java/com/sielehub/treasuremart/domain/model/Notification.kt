package com.sielehub.treasuremart.domain.model

data class Notification(
    val notifId: Long = 0L,
    val message: String = "",
    var read: Boolean = false
)
