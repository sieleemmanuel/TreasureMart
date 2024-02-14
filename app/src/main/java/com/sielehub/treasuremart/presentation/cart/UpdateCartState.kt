package com.sielehub.treasuremart.presentation.cart

import com.sielehub.treasuremart.domain.model.Cart

data class UpdateCartState(
    val isLoading: Boolean = false,
    val cart: Cart? = null,
    val error: String = ""
)
