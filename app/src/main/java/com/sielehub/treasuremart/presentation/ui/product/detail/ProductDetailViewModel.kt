package com.sielehub.treasuremart.presentation.ui.product.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.model.CartProduct
import com.sielehub.treasuremart.domain.model.WishProduct
import com.sielehub.treasuremart.domain.use_case.cart.AddProductToCartUseCase
import com.sielehub.treasuremart.domain.use_case.cart.GetCartUseCase
import com.sielehub.treasuremart.domain.use_case.product.GetProductUseCase
import com.sielehub.treasuremart.domain.use_case.product.wishlist.AddToWishListUseCase
import com.sielehub.treasuremart.domain.use_case.product.wishlist.CheckIsWishUseCase
import com.sielehub.treasuremart.domain.use_case.product.wishlist.RemoveFromWishListUseCase
import com.sielehub.treasuremart.presentation.ui.cart.CartState
import com.sielehub.treasuremart.presentation.ui.product.wish.RemoveFromWishListState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val getProductUseCase: GetProductUseCase,
    private val addToWishListUseCase: AddToWishListUseCase,
    private val removeFromWishListUseCase: RemoveFromWishListUseCase,
    private val checkIsWishUseCase: CheckIsWishUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val getCartUseCase: GetCartUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _productDetailState = MutableStateFlow(ProductDetailState())
    val productDetailState: StateFlow<ProductDetailState> = _productDetailState

    private val _addToWishListState = MutableStateFlow(AddToWishListState())
    val addToWishListState: StateFlow<AddToWishListState> = _addToWishListState

    private val _removeFromWishListState = MutableStateFlow(RemoveFromWishListState())
    val removeFromWishListState: StateFlow<RemoveFromWishListState> = _removeFromWishListState

    private val _isWishProduct = MutableStateFlow(false)
    val isWishProduct: StateFlow<Boolean> = _isWishProduct

    private val _addToCartState = MutableStateFlow(AddToCartState())
    val addToCartState: StateFlow<AddToCartState> = _addToCartState

    private val _productInCartState = MutableStateFlow(ProductInCartState())
    val productInCartState: StateFlow<ProductInCartState> = _productInCartState

    private val _cartState = MutableStateFlow(CartState())
    val cartState: StateFlow<CartState> = _cartState

    /*init {
        savedStateHandle.get<Int>(Constants.Companion.PARAM_PRODUCT_ID)?.let { productId ->
            getProduct(productId)
        }
    }*/

    fun getProductDetail(id: Int) {
        getProductUseCase(id).onEach { result ->
            _productDetailState.update {
                when (result) {
                    is Resource.Success -> {
                        ProductDetailState(product = result.data)
                    }

                    is Resource.Error -> {
                        ProductDetailState(error = result.message ?: "Unknown error occurred")
                    }

                    is Resource.Loading -> {
                        ProductDetailState(isLoading = true)
                    }

                }
            }
        }.launchIn(viewModelScope)
    }

    fun addToWishList(product: WishProduct) {
        viewModelScope.launch(Dispatchers.IO) {
            addToWishListUseCase(product).collect { result ->
                _addToWishListState.update {
                    when (result) {
                        is Resource.Success -> {
                            AddToWishListState(success = result.data)
                        }

                        is Resource.Error -> {
                            AddToWishListState(error = result.message ?: "Unknown error occurred")
                        }

                        is Resource.Loading -> {
                            AddToWishListState(isLoading = true)
                        }
                    }
                }
                checkIsWish(product.productId)
            }
        }
    }

    fun checkIsWish(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            checkIsWishUseCase(productId).collect { result ->
                _isWishProduct.update {
                    result
                }
            }
        }
    }

    fun removeFromWishList(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            removeFromWishListUseCase(productId).collect { result ->
                _removeFromWishListState.update {
                    when (result) {
                        is Resource.Error -> RemoveFromWishListState(error = result.message)
                        is Resource.Loading -> RemoveFromWishListState(isLoading = true)
                        is Resource.Success -> RemoveFromWishListState(
                            success = result.data == Unit
                        )
                    }
                }
                checkIsWish(productId)
            }
        }
    }

    fun addToCart(cartProduct: CartProduct) {
        viewModelScope.launch(Dispatchers.IO) {
            addProductToCartUseCase(cartProduct).collect { result ->
                _addToCartState.update {
                    when (result) {
                        is Resource.Error -> AddToCartState(error = result.message ?: "")
                        is Resource.Success -> AddToCartState(added = result.data != null)
                        else -> it
                    }
                }
            }
        }
    }

    fun isProductInCart(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getCartUseCase(false).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _cartState.update {
                            CartState(isLoading = true)
                        }
                    }

                    is Resource.Error -> {
                        _cartState.update {
                            CartState(
                                error = result.message ?: "Unknown error occurred getting cart"
                            )
                        }
                    }

                    is Resource.Success -> {
                        _cartState.update {
                            CartState(cart = result.data)
                        }
                        _productInCartState.update {
                            ProductInCartState(
                                inCart = result.data?.products?.any { it.productId == productId } == true,
                                cartProduct = result.data?.products?.find { it.productId == productId }
                            )
                        }
                    }
                }
            }
        }
    }
}