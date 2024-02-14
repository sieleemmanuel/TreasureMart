package com.sielehub.treasuremart.presentation.cart

import com.sielehub.treasuremart.domain.model.Cart

data class CreateCartState(
    val isLoading: Boolean = false,
    val cart: Cart? = null,
    val error: String = ""
)
