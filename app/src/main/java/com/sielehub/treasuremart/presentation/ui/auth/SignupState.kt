package com.sielehub.treasuremart.presentation.ui.auth

import com.sielehub.treasuremart.domain.model.SignupRequest

data class SignupState(
    val isLoading: Boolean = false,
    val signupRequest: SignupRequest? = null,
    val error: String = ""
)
