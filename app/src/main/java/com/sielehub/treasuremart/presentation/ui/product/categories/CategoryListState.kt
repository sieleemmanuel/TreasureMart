package com.sielehub.treasuremart.presentation.ui.product.categories

import com.sielehub.treasuremart.core.Constants

data class CategoryListState(
    val isLoading: Boolean = false,
    val categories: List<String> = Constants.categories(),
    val error: String = ""
)
