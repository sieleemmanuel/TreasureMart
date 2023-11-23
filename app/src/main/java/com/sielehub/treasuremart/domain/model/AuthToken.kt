package com.sielehub.treasuremart.domain.model

data class AuthToken(
    val access_token: String,
    val refresh_token: String
)