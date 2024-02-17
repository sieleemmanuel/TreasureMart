package com.sielehub.treasuremart.presentation.cart

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.model.Cart
import com.sielehub.treasuremart.domain.use_case.cart.CreateCartUseCase
import com.sielehub.treasuremart.domain.use_case.cart.GetCartUseCase
import com.sielehub.treasuremart.domain.use_case.cart.GetCartsUseCase
import com.sielehub.treasuremart.domain.use_case.cart.UpdateCartUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CartViewModel(
    private val createCartUseCase: CreateCartUseCase,
    private val getCartUseCase: GetCartUseCase,
    private val getCartsUseCase: GetCartsUseCase,
    private val updateCartUseCase: UpdateCartUseCase
) : ViewModel() {

    private val _cartListState = mutableStateOf(CartListState())
    val cartListState: State<CartListState> = _cartListState

    private val _cartState = mutableStateOf(CartState())
    val cartState: State<CartState> = _cartState

    private val _createCartState = mutableStateOf(CreateCartState())
    val createCartState: State<CreateCartState> = _createCartState

    private val _updateCartState = mutableStateOf(UpdateCartState())
    val updateCartState: State<UpdateCartState> = _updateCartState

    fun createCart(cart: Cart) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = createCartUseCase.invoke(cart)) {
                is Resource.Success -> {
                    _createCartState.value = CreateCartState(cart = result.data)
                }

                is Resource.Error -> {
                    _createCartState.value =
                        CreateCartState(error = result.message ?: "Unknown error occurred")
                }

                is Resource.Loading -> {
                    _createCartState.value = CreateCartState(isLoading = true)
                }

            }
        }
    }

    fun getCart(cartId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getCartUseCase.invoke(cartId).onEach {
                when (val result = it) {
                    is Resource.Success -> {
                        _cartState.value = CartState(cart = result.data)
                    }

                    is Resource.Error -> {
                        _cartState.value =
                            CartState(error = result.message ?: "Unknown error occurred")
                    }

                    is Resource.Loading -> {
                        _cartState.value = CartState(isLoading = true)
                    }

                }
            }.launchIn(this)
        }
    }

    fun getCarts() {
        viewModelScope.launch(Dispatchers.IO) {
            getCartsUseCase.invoke().onEach {
                when (val result = it) {
                    is Resource.Success -> {
                        _cartListState.value = CartListState(carts = result.data ?: emptyList())
                    }

                    is Resource.Error -> {
                        _cartListState.value =
                            CartListState(error = result.message ?: "Unknown error occurred")
                    }

                    is Resource.Loading -> {
                        _cartListState.value = CartListState(isLoading = true)
                    }

                }
            }.launchIn(this)
        }
    }

    fun updateCart(cart: Cart) {
        viewModelScope.launch(Dispatchers.IO) {

            when (val result = updateCartUseCase.invoke(cart)) {
                is Resource.Success -> {
                    _updateCartState.value = UpdateCartState(cart = result.data)
                }

                is Resource.Error -> {
                    _updateCartState.value =
                        UpdateCartState(error = result.message ?: "Unknown error occurred")
                }

                is Resource.Loading -> {
                    _updateCartState.value = UpdateCartState(isLoading = true)
                }

            }
        }
    }

}
