package com.sielehub.treasuremart.presentation.notifications

import com.sielehub.treasuremart.domain.model.Notification

data class NotificationsState(
    val isLoading: Boolean = false,
    val notifications: List<Notification> = emptyList(),
    val error: String = ""
)
