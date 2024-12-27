package com.sielehub.treasuremart.presentation.categories

import com.sielehub.treasuremart.core.Constants

data class CategoryListState(
    val isLoading: Boolean = false,
    val categories: List<String> = Constants.categories().map { it.first },
    val error: String = ""
)
