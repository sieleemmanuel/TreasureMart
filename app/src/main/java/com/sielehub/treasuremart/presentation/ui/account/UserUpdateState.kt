package com.sielehub.treasuremart.presentation.ui.account

import com.sielehub.treasuremart.domain.model.User

data class UserUpdateState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String = ""
)
