package com.sielehub.treasuremart.presentation.ui.checkout

data class PlaceOrderState(
    val isLoading: Boolean = false,
    val isSuccessful: Boolean = false,
    val error: String = ""
)
