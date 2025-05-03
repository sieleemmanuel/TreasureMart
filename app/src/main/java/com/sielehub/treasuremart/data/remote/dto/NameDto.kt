package com.sielehub.treasuremart.data.remote.dto

import com.sielehub.treasuremart.domain.model.Name
import kotlinx.serialization.Serializable

@Serializable
data class NameDto(
    val firstname: String,
    val lastname: String
) {
    fun toName() = Name(firstname, lastname)
}