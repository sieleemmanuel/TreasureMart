package com.sielehub.treasuremart.presentation.ui.dashboard

import com.sielehub.treasuremart.domain.model.Product

data class SuperDealsProductsState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val error: String = ""
)
