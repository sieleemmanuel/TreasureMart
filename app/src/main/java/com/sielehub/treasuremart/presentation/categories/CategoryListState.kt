package com.sielehub.treasuremart.presentation.categories

data class CategoryListState(
    val isLoading: Boolean = false,
    val categories: List<String> = emptyList(),
    val error: String = ""
)
