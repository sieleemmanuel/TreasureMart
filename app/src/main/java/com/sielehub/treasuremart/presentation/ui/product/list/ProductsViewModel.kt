package com.sielehub.treasuremart.presentation.ui.product.list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.use_case.product.GetProductsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProductsViewModel(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _productsState = MutableStateFlow(ProductListState())
    val productsState: StateFlow<ProductListState> = _productsState.asStateFlow()


    private val _productByCategoryState = mutableStateOf(ProductsByCategoryState())
    val productByCategoryState: State<ProductsByCategoryState> = _productByCategoryState


    fun getProducts(productsQuery: String? = null) {
        getProductsUseCase(productsQuery).onEach {
            when (val result = it) {
                is Resource.Success -> {
                    _productsState.value = ProductListState(products = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    Log.d(
                        ProductsViewModel::class.simpleName,
                        "Error getting Products: ${result.message}"
                    )
                    _productsState.value =
                        ProductListState(error = result.message ?: "Unknown error occurred")
                }

                is Resource.Loading -> {
                    _productsState.value = ProductListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}