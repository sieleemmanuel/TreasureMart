package com.sielehub.treasuremart.domain.model

data class Cart(
    val date: String,
    val id: Int,
    val products: List<CartProduct>,
    val userId: Int
)