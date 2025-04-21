package com.sielehub.treasuremart.presentation.ui.product.list

import com.sielehub.treasuremart.domain.model.Product

data class ProductsByCategoryState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val error: String = ""
)