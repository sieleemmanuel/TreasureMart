package com.sielehub.treasuremart.presentation.ui.product.wish

import com.sielehub.treasuremart.domain.model.Product

data class WishListState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val wishList: List<Product> = emptyList()

)
