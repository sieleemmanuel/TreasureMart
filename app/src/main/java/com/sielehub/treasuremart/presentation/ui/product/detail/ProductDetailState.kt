package com.sielehub.treasuremart.presentation.ui.product.detail

import com.sielehub.treasuremart.domain.model.Product

data class ProductDetailState(
    val isLoading: Boolean = false,
    val product: Product? = null,
    val error: String = ""
)