package com.sielehub.treasuremart.presentation.user

import com.sielehub.treasuremart.domain.model.User

data class CreateUserState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String = ""
)
