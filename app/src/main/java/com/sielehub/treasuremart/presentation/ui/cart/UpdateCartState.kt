package com.sielehub.treasuremart.presentation.ui.cart

data class UpdateCartState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String = ""
)
