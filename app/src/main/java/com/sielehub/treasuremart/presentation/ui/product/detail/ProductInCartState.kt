package com.sielehub.treasuremart.presentation.ui.product.detail

import com.sielehub.treasuremart.domain.model.CartProduct

data class ProductInCartState(
    val inCart: Boolean = false,
    val cartProduct: CartProduct? = null
)