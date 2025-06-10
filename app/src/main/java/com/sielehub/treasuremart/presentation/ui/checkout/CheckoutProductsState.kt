package com.sielehub.treasuremart.presentation.ui.checkout

import com.sielehub.treasuremart.domain.model.CartProduct

data class CheckoutProductsState(
    val isLoading: Boolean = false,
    val products: List<CartProduct> = emptyList(),
    val error: String = ""
)
