package com.sielehub.treasuremart.presentation.product

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.use_case.product.GetProductUseCase
import com.sielehub.treasuremart.domain.use_case.product.GetProductsByCategoryUseCase
import com.sielehub.treasuremart.domain.use_case.product.GetProductsUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProductViewModel(
    private val getProductUseCase: GetProductUseCase,
    private val getProductsUseCase: GetProductsUseCase,
    private val getProductsByCategoryUseCase: GetProductsByCategoryUseCase
) : ViewModel() {

    private val _productsState = mutableStateOf(ProductListState())
    val productsState: State<ProductListState> = _productsState

    private val _productState = mutableStateOf(ProductState())
    val productState: State<ProductState> = _productState

    private val _productByCategoryState = mutableStateOf(ProductsByCategoryState())
    val productByCategoryState: State<ProductsByCategoryState> = _productByCategoryState

    fun getProducts() {
        getProductsUseCase().onEach {
            when (val result = it) {
                is Resource.Success -> {
                    _productsState.value = ProductListState(products = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    _productsState.value =
                        ProductListState(error = result.message ?: "Unknown error occurred")
                }

                is Resource.Loading -> {
                    _productsState.value = ProductListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getProductsByCategory(category: String) {
        getProductsByCategoryUseCase(category).onEach {
            when (val result = it) {
                is Resource.Success -> {
                    _productByCategoryState.value =
                        ProductsByCategoryState(products = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    _productByCategoryState.value =
                        ProductsByCategoryState(error = result.message ?: "Unknown error occurred")
                }

                is Resource.Loading -> {
                    _productByCategoryState.value = ProductsByCategoryState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getProduct(id: Int) {
        getProductUseCase(id).onEach {
            when (val result = it) {
                is Resource.Success -> {
                    _productState.value = ProductState(product = result.data)
                }

                is Resource.Error -> {
                    _productState.value =
                        ProductState(error = result.message ?: "Unknown error occurred")
                }

                is Resource.Loading -> {
                    _productState.value = ProductState(isLoading = true)
                }

            }
        }.launchIn(viewModelScope)
    }

}