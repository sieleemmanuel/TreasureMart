package com.sielehub.treasuremart.data.remote.dto

import com.sielehub.treasuremart.domain.model.CartProduct

data class CartProductDto(
    val productId: Int,
    val quantity: Int
){
    fun toCartProduct():CartProduct {
        return CartProduct(
            productId = productId,
            quantity = quantity
        )
    }
}