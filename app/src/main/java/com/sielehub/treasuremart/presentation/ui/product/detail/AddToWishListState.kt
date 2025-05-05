package com.sielehub.treasuremart.presentation.ui.product.detail

data class AddToWishListState(
    val isLoading: Boolean = false,
    val success: Unit? = null,
    val error: String = ""
)