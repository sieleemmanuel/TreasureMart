package com.sielehub.treasuremart.data.remote.dto

import com.sielehub.treasuremart.domain.model.User

data class UserDto(
    val address: AddressDto,
    val email: String,
    val id: Int,
    val name: NameDto,
    val password: String,
    val phone: String,
    val username: String
) {
    fun toUser(): User {
        return User(
            address = address.toAddress(),
            email = email,
            id = id,
            name = name.toName(),
            password = password,
            phone = phone,
            username = username
        )
    }

}