package com.sielehub.treasuremart.presentation.auth

data class LoginState(
    val isLoading: Boolean = false,
    val token: String? = null,
    val error: String? = null
)
