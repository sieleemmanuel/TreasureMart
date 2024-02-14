package com.sielehub.treasuremart.presentation.product

import com.sielehub.treasuremart.domain.model.Product

data class ProductsByCategoryState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val error: String = ""
)
