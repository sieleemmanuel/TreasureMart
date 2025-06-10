package com.sielehub.treasuremart.presentation.ui.orders

import com.sielehub.treasuremart.domain.model.Order

data class OrderState(
    val isLoading: Boolean = false,
    val order: Order? = null,
    val error: String = ""
)
