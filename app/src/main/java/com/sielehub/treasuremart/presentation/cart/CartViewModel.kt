package com.sielehub.treasuremart.presentation.cart

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sielehub.treasuremart.domain.model.Cart
import com.sielehub.treasuremart.domain.use_case.cart.CreateCartUseCase
import com.sielehub.treasuremart.domain.use_case.cart.GetCartUseCase
import com.sielehub.treasuremart.domain.use_case.cart.GetCartsUseCase
import com.sielehub.treasuremart.domain.use_case.cart.UpdateCartUseCase
import kotlinx.coroutines.Dispatchers
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

    private val _createCartState = mutableStateOf<Cart?>(null)
    val createCartState: State<Cart?> = _createCartState

    private val _updateCartState = mutableStateOf<Cart?>(null)
    val updateCartState: State<Cart?> = _updateCartState

    fun createCart(cart: Cart) {
        viewModelScope.launch(Dispatchers.IO) {
            createCartUseCase.invoke(cart)
        }
    }

    fun getCart(cartId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getCartUseCase.invoke(cartId)
        }
    }

    fun getCarts() {
        viewModelScope.launch(Dispatchers.IO) {
            getCartsUseCase.invoke()
        }
    }

    fun updateCart(cart: Cart) {
        viewModelScope.launch(Dispatchers.IO) {
            updateCartUseCase.invoke(cart)
        }
    }

}
