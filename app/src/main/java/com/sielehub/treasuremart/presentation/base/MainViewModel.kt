package com.sielehub.treasuremart.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.use_case.cart.GetCartUseCase
import com.sielehub.treasuremart.presentation.ui.cart.CartState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val getCartUseCase: GetCartUseCase,
) : ViewModel() {

    private val _cartState = MutableStateFlow(CartState())
    val cartState: StateFlow<CartState> = _cartState.asStateFlow()

    fun getCart(showLoading: Boolean = true) {
        viewModelScope.launch(Dispatchers.IO) {
            getCartUseCase.invoke(showLoading).onEach {
                when (val result = it) {
                    is Resource.Success -> {
                        _cartState.update { CartState(cart = result.data) }
                    }

                    is Resource.Error -> {
                        _cartState.update {
                            CartState(error = result.message ?: "Unknown error occurred")
                        }
                    }

                    is Resource.Loading -> {
                        _cartState.update { CartState(isLoading = true) }
                    }

                }
            }.launchIn(this)
        }
    }

    init {
        getCart()
    }
}