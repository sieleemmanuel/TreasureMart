package com.sielehub.treasuremart.presentation.ui.product.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.use_case.product.GetProductUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class ProductDetailViewModel(
    private val getProductUseCase: GetProductUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _productDetailState = MutableStateFlow(ProductDetailState())
    val productDetailState: StateFlow<ProductDetailState> = _productDetailState

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

}