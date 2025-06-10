package com.sielehub.treasuremart.presentation.ui.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.use_case.orders.GetOrderUseCase
import com.sielehub.treasuremart.domain.use_case.orders.GetOrdersUseCase
import com.sielehub.treasuremart.presentation.ui.checkout.PlaceOrderState
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
        viewModelScope.launch {

        }
    }

    fun getOrder(orderId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            getOrdersUseCase().collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        _placeOrderState.value = PlaceOrderState(isSuccessful = true)
                        updateCartProductsUseCase
                    }

                    is Resource.Error -> {
                        _placeOrderState.value =
                            PlaceOrderState(error = result.message ?: "An unknown error occurred")
                    }

                    is Resource.Loading -> {
                        _placeOrderState.value = PlaceOrderState(isLoading = true)
                    }
                }
            }
        }
    }

    init {
        getCheckoutProducts()
        getSubtotalAmount()
        getAddresses()
    }

}