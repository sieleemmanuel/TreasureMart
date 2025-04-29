package com.sielehub.treasuremart.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val address: Address? = null,
    val email: String,
    val id: Int? = null,
    val name: Name,
    val password: String,
    val phone: String,
    val username: String
)

@Serializable
data class Name(
    val firstname: String,
    val lastname: String
)

@Serializable
data class Address(
    val city: String,
    val geolocation: Geolocation,
    val number: Int,
    val street: String,
    val zipcode: String
)

@Serializable
data class Geolocation(
    val lat: String,
    val long: String
)

@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)

@Serializable
data class SignupRequest(
    @SerialName("username")
    val username: String,
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String,
)

@Serializable
data class SignupResponse(
    @SerialName("id")
    val id: Int
)