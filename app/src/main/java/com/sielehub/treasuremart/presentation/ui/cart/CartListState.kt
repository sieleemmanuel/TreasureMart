package com.sielehub.treasuremart.presentation.ui.cart

import com.sielehub.treasuremart.domain.model.Cart

data class CartListState(
    val isLoading: Boolean = false,
    val carts: List<Cart> = emptyList(),
    val error: String = ""
)
