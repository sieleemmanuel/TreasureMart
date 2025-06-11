package com.sielehub.treasuremart.presentation.ui.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.use_case.orders.GetOrderUseCase
import com.sielehub.treasuremart.domain.use_case.orders.GetOrdersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class OrdersViewModel(
    private val getOrdersUseCase: GetOrdersUseCase,
    private val getOrderUseCase: GetOrderUseCase,
) : ViewModel() {

    private val _ordersState = MutableStateFlow<OrdersState>(OrdersState())
    val ordersState: StateFlow<OrdersState> = _ordersState.asStateFlow()

    private val _orderState = MutableStateFlow<OrderState>(OrderState())
    val orderState: StateFlow<OrderState> = _orderState.asStateFlow()

    fun getOrders() {
        viewModelScope.launch(Dispatchers.IO) {
            getOrdersUseCase().collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        _ordersState.value = OrdersState(orders = result.data ?: emptyList())
                    }

                    is Resource.Error -> {
                        _ordersState.value = OrdersState(
                            error = result.message ?: "An unknown error occurred"
                        )
                    }

                    is Resource.Loading -> {
                        _ordersState.value = OrdersState(isLoading = true)
                    }
                }
            }
        }
    }

    fun getOrder(orderId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            getOrderUseCase(orderId).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        _orderState.value = OrderState(order = result.data)
                    }

                    is Resource.Error -> {
                        _orderState.value = OrderState(
                            error = result.message ?: "An unknown error occurred"
                        )
                    }

                    is Resource.Loading -> {
                        _orderState.value = OrderState(isLoading = true)
                    }
                }
            }
        }
    }

    init {
        getOrders()
    }

}