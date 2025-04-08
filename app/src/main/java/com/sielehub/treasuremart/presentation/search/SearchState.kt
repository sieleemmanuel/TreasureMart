package com.sielehub.treasuremart.presentation.search

import com.sielehub.treasuremart.domain.model.Product

data class SearchState(
    val isLoading: Boolean = false,
    val searchedProducts: List<Product> = emptyList(),
    val error: String = ""
)
