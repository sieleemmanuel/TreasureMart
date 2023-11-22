package com.sielehub.treasuremart.data.remote.dto

import com.sielehub.treasuremart.domain.model.Category

data class CategoryDto(
    val id: Int,
    val image: String,
    val name: String
)

fun CategoryDto.toCategory() = Category(
    id = id,
    image = image,
    name = name
)