package com.sielehub.treasuremart.presentation.cart

import com.sielehub.treasuremart.domain.model.Cart

data class CartState(
    val isLoading: Boolean = false,
    val carts: Cart? = null,
    val error: String = ""
)
