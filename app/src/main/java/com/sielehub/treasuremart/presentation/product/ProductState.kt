package com.sielehub.treasuremart.presentation.product

import com.sielehub.treasuremart.domain.model.Product
import com.sielehub.treasuremart.domain.model.User

data class ProductState(
    val isLoading: Boolean = false,
    val product: Product? = null,
    val error: String = ""
)
