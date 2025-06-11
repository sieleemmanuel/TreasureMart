package com.sielehub.treasuremart.presentation.ui.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.model.Address
import com.sielehub.treasuremart.domain.model.Geolocation
import com.sielehub.treasuremart.domain.model.Order
import com.sielehub.treasuremart.domain.use_case.cart.GetCartAmountUseCase
import com.sielehub.treasuremart.domain.use_case.cart.GetCartUseCase
import com.sielehub.treasuremart.domain.use_case.cart.UpdateCartProductsUseCase
import com.sielehub.treasuremart.domain.use_case.checkout.PlaceOrderUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CheckoutViewModel(
    private val getCartUseCase: GetCartUseCase,
    private val getCartAmountUseCase: GetCartAmountUseCase,
    private val placeOrderUseCase: PlaceOrderUseCase,
    private val updateCartProductsUseCase: UpdateCartProductsUseCase
) : ViewModel() {

    private val _checkoutProductsState = MutableStateFlow(CheckoutProductsState())
    val checkoutProductsState: StateFlow<CheckoutProductsState> = _checkoutProductsState

    private val _subtotal = MutableStateFlow(0.00)
    val subtotal: StateFlow<Double> = _subtotal

    private val _addresses = MutableStateFlow<List<Address>>(emptyList())
    val addresses: StateFlow<List<Address>> = _addresses

    private val _placeOrderState = MutableStateFlow<PlaceOrderState>(PlaceOrderState())
    val placeOrderState: StateFlow<PlaceOrderState> = _placeOrderState.asStateFlow()

    fun getCheckoutProducts() {
        viewModelScope.launch {
            getCartUseCase(false).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        _checkoutProductsState.value = CheckoutProductsState(
                            products = result.data?.products?.filter { it.isSelected }
                                ?: emptyList()
                        )
                    }

                    is Resource.Error -> {
                        _checkoutProductsState.value = CheckoutProductsState(
                            error = result.message ?: "An unknown error occurred"
                        )
                    }

                    is Resource.Loading -> {
                        _checkoutProductsState.value = CheckoutProductsState(isLoading = true)
                    }

                }
            }
        }
    }

    fun getSubtotalAmount() {
        viewModelScope.launch {
            getCartAmountUseCase().collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        _subtotal.value = result.data ?: 0.0
                    }

                    is Resource.Error -> {
                        _subtotal.value = 0.0
                    }

                    is Resource.Loading -> {
                        _subtotal.value = 0.0
                    }
                }
            }
        }
    }

    fun getAddresses() {
        viewModelScope.launch {
            val addresses = mutableListOf(
                Address(
                    city = "Pretoria",
                    number = 123456789,
                    street = "Address 1",
                    geolocation = Geolocation("123.456", "789.012"),
                    zipcode = "12345",
                    shippingFee = 258.00,
                    isDefault = true
                ),
                Address(
                    city = "Belin",
                    number = 123456789,
                    street = "Address 2",
                    geolocation = Geolocation("345.678", "901.234"),
                    zipcode = "23567",
                    shippingFee = 150.00
                ),
                Address(
                    city = "Maputo",
                    number = 123456789,
                    street = "Address 3",
                    geolocation = Geolocation("234.567", "890.123"),
                    zipcode = "205789",
                    shippingFee = 300.00
                )
            )
            _addresses.value = addresses
        }
    }

    fun placeOrder(order: Order) {
        viewModelScope.launch(Dispatchers.IO) {
            placeOrderUseCase(order).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        _placeOrderState.value = PlaceOrderState(orderID = result.data?.orderId)
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