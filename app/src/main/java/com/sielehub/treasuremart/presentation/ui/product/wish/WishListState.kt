package com.sielehub.treasuremart.presentation.ui.product.wish

import com.sielehub.treasuremart.domain.model.WishProduct

data class WishListState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val wishList: List<WishProduct> = emptyList()

)
