package com.sielehub.treasuremart.data.remote.dto

import com.sielehub.treasuremart.domain.model.Product

data class ProductDto(
    val category: CategoryDto,
    val description: String,
    val id: Int,
    val images: List<String>,
    val price: Int,
    val title: String
)

fun ProductDto.toProduct() = Product(
    category = category.toCategory(),
    description = description,
    id = id,
    images = images,
    price = price,
    title = title
)