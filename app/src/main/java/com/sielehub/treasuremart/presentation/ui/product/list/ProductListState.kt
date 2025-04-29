package com.sielehub.treasuremart.presentation.ui.product.list

import com.sielehub.treasuremart.core.Constants
import com.sielehub.treasuremart.domain.model.Product

data class ProductListState(
    val isLoading: Boolean = false,
    val products: List<Product> = Constants.Companion.products(),
    val error: String = ""
)