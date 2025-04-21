package com.sielehub.treasuremart.presentation.ui.cart

import com.sielehub.treasuremart.domain.model.Cart

data class CartState(
    val isLoading: Boolean = false,
    val cart: Cart? = null,
    val error: String = ""
)
