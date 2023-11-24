package com.sielehub.treasuremart.data.remote.dto

import com.sielehub.treasuremart.domain.model.Cart

data class CartDto(
    val date: String,
    val id: Int,
    val products: List<CartProductDto>,
    val userId: Int
){
    fun toCart() = Cart(
        date = date,
        id = id,
        products = products.map { it.toCartProduct()},
        userId = userId
    )
}