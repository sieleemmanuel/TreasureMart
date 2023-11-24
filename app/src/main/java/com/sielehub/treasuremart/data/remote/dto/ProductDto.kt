package com.sielehub.treasuremart.data.remote.dto

import com.sielehub.treasuremart.domain.model.Product

data class ProductDto(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: RatingDto,
    val title: String
){
    fun toProduct() = Product(
        category = category,
        description = description,
        id = id,
        image = image,
        price = price,
        title = title,
        rating = rating.toRating()
    )
}