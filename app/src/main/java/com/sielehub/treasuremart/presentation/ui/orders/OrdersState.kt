package com.sielehub.treasuremart.presentation.ui.orders

import com.sielehub.treasuremart.domain.model.Order

data class OrdersState(
    val isLoading: Boolean = false,
    val orders: List<Order> = emptyList(),
    val error: String = ""
)
