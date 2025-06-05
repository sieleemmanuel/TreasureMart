package com.sielehub.treasuremart.presentation.ui.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.model.Cart
import com.sielehub.treasuremart.domain.model.CartProduct
import com.sielehub.treasuremart.domain.model.Product
import com.sielehub.treasuremart.domain.use_case.cart.AddProductToCartUseCase
import com.sielehub.treasuremart.domain.use_case.cart.CreateCartUseCase
import com.sielehub.treasuremart.domain.use_case.cart.GetCartAmountUseCase
import com.sielehub.treasuremart.domain.use_case.cart.GetCartUseCase
import com.sielehub.treasuremart.domain.use_case.cart.GetCartsUseCase
import com.sielehub.treasuremart.domain.use_case.cart.IncreaseQuantityUseCase
import com.sielehub.treasuremart.domain.use_case.cart.ReduceQuantityUseCase
import com.sielehub.treasuremart.domain.use_case.cart.UpdateCartUseCase
import com.sielehub.treasuremart.domain.use_case.product.GetProductUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CartViewModel(
    private val createCartUseCase: CreateCartUseCase,
    private val getCartUseCase: GetCartUseCase,
    private val getCartsUseCase: GetCartsUseCase,
    private val updateCartUseCase: UpdateCartUseCase,
    private val getProductUseCase: GetProductUseCase,
    private val reduceQuantityUseCase: ReduceQuantityUseCase,
    private val increaseQuantityUseCase: IncreaseQuantityUseCase,
    private val getCartAmountUseCase: GetCartAmountUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase
) : ViewModel() {

    private val _cartListState = MutableStateFlow(CartListState())
    val cartListState: StateFlow<CartListState> = _cartListState

    private val _cartState = MutableStateFlow(CartState())
    val cartState: StateFlow<CartState> = _cartState.asStateFlow()

    private val _createCartState = MutableStateFlow(CreateCartState())
    val createCartState: StateFlow<CreateCartState> = _createCartState.asStateFlow()

    private val _updateCartState = MutableStateFlow(UpdateCartState())
    val updateCartState: StateFlow<UpdateCartState> = _updateCartState.asStateFlow()

    private val _cartAmountState = MutableStateFlow(0.0)
    val cartAmountState: StateFlow<Double> = _cartAmountState.asStateFlow()

    fun createCart(cart: Cart) {
        viewModelScope.launch(Dispatchers.IO) {
            createCartUseCase(cart).onEach { result ->
                when (result) {
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

            }.launchIn(this)
        }
    }

    fun getCart(showLoading: Boolean = true) {
        viewModelScope.launch(Dispatchers.IO) {
            getCartUseCase(showLoading).onEach {
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
        getCartAmount()
    }

    fun getCarts() {
        viewModelScope.launch(Dispatchers.IO) {
            getCartsUseCase.invoke().onEach { result ->
                when (result) {
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
            updateCartUseCase(cart).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _updateCartState.value = UpdateCartState(success = result.data == true)
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

    fun getProduct(id: Int, product: (Product) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            getProductUseCase(id).collect {
                it.data?.let { it1 ->
                    product(it1)
                }
            }
        }
    }

    fun reduceQuantity(cartProduct: CartProduct) {
        viewModelScope.launch(Dispatchers.IO) {
            reduceQuantityUseCase(cartProduct).collect {
                it.data?.let {
                    //  _cartState.value = CartState(cart = it)
                }
            }
            getCart(showLoading = false)
        }
    }

    fun increaseQuantity(cartProduct: CartProduct) {
        viewModelScope.launch(Dispatchers.IO) {
            increaseQuantityUseCase(cartProduct).collect {
                it.data?.let {
                    //  _cartState.value = CartState(cart = it)
                }
            }
            getCart(false)
        }
    }

    fun getCartAmount() {
        viewModelScope.launch(Dispatchers.IO) {
            val cartProducts = mutableListOf<Product>()
            getCartUseCase(false).collect {
                Log.d(TAG, "Cart: ${it.data}")
                it.data?.products?.forEach { cartProduct ->
                    getProductUseCase(cartProduct.productId).collect {
                        it.data?.let { product ->
                            cartProducts.add(product)
                        }
                    }
                }
            }
            getCartAmountUseCase(cartState.value.cart!!, cartProducts).collect {
                when (it) {
                    is Resource.Success -> {
                        _cartAmountState.value = it.data ?: 0.0
                    }

                    is Resource.Error -> {
                        _cartAmountState.value = 0.0
                    }

                    is Resource.Loading -> {
                        _cartAmountState.value = 0.0
                    }
                }
            }
        }
    }

    init {
        getCarts()
        getCart()
    }

    companion object {
        private const val TAG = "CartViewModel"
    }

}
