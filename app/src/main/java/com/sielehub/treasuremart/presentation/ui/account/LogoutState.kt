package com.sielehub.treasuremart.presentation.ui.account

data class LogoutState(
    val isLoading: Boolean = false,
    val isLoggedOut: Boolean = false,
    val error: String = ""
)
