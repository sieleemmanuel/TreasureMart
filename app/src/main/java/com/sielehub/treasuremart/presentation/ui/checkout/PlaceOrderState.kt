package com.sielehub.treasuremart.presentation.ui.checkout

data class PlaceOrderState(
    val isLoading: Boolean = false,
    val orderID: Long? = null,
    val error: String = ""
)
