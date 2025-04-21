package com.sielehub.treasuremart.presentation.ui.dashboard

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.use_case.product.GetBestPickProductsUseCase
import com.sielehub.treasuremart.domain.use_case.product.GetProductsByCategoryUseCase
import com.sielehub.treasuremart.domain.use_case.product.GetSuperDealsProductsUseCase
import com.sielehub.treasuremart.presentation.ui.product.list.ProductsByCategoryState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val getProductsByCategoryUseCase: GetProductsByCategoryUseCase,
    private val getSuperDealsProductsUseCase: GetSuperDealsProductsUseCase,
    private val getBestPickProductsUseCase: GetBestPickProductsUseCase
) : ViewModel() {

    private val _superDealsProductsState =
        mutableStateOf<SuperDealsProductsState>(SuperDealsProductsState(isLoading = true))
    val superDealsProductsState: State<SuperDealsProductsState> = _superDealsProductsState

    private val _bestPickProductsState =
        mutableStateOf<BestPickProductsState>(BestPickProductsState(isLoading = true))
    val bestPickProductsState: State<BestPickProductsState> = _bestPickProductsState

    private val _productsByCategoryState = mutableStateOf(ProductsByCategoryState())
    val productSByCategoryState: State<ProductsByCategoryState> = _productsByCategoryState


    fun getSuperDealProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            getSuperDealsProductsUseCase().collect { productsState ->
                when (productsState) {
                    is Resource.Success -> {
                        _superDealsProductsState.value = SuperDealsProductsState(
                            products = productsState.data ?: emptyList()
                        )
                    }

                    is Resource.Error -> {
                        _superDealsProductsState.value =
                            SuperDealsProductsState(
                                error = productsState.message ?: "An error occurred"
                            )
                    }

                    is Resource.Loading -> {
                        _superDealsProductsState.value = SuperDealsProductsState(isLoading = true)
                    }
                }
            }
        }
    }

    fun getBestPickProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            getBestPickProductsUseCase().collect { productsState ->
                when (productsState) {
                    is Resource.Success -> {
                        _bestPickProductsState.value =
                            BestPickProductsState(products = productsState.data ?: emptyList())
                    }

                    is Resource.Error -> {
                        _bestPickProductsState.value =
                            BestPickProductsState(
                                error = productsState.message ?: "An error occurred"
                            )
                    }

                    is Resource.Loading -> {
                        _bestPickProductsState.value = BestPickProductsState(isLoading = true)
                    }
                }
            }
        }
    }

    fun getProductsByCategory(category: String) {
        getProductsByCategoryUseCase(category).onEach {
            when (val result = it) {
                is Resource.Success -> {
                    Log.d(TAG, "$category: ${result.data}")
                    _productsByCategoryState.value =
                        ProductsByCategoryState(products = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    _productsByCategoryState.value =
                        ProductsByCategoryState(error = result.message ?: "Unknown error occurred")
                }

                is Resource.Loading -> {
                    _productsByCategoryState.value = ProductsByCategoryState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    init {
        getSuperDealProducts()
        getBestPickProducts()
    }

    companion object {
        private const val TAG = "DashboardViewModel"
    }

}