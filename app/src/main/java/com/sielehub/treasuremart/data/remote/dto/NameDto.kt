package com.sielehub.treasuremart.data.remote.dto

import com.sielehub.treasuremart.domain.model.Name

data class NameDto(
    val firstname: String,
    val lastname: String
){
    fun toName() = Name(firstname, lastname)
}