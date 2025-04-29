package com.sielehub.treasuremart.presentation.ui.auth

import com.sielehub.treasuremart.domain.model.SignupResponse

data class SignupState(
    val isLoading: Boolean = false,
    val signupResponse: SignupResponse? = null,
    val error: String = ""
)
