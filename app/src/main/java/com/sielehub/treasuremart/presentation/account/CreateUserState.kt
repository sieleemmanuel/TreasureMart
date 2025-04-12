package com.sielehub.treasuremart.presentation.account

import com.sielehub.treasuremart.domain.model.SignupRequest

data class CreateUserState(
    val isLoading: Boolean = false,
    val signupRequest: SignupRequest? = null,
    val error: String = ""
)
