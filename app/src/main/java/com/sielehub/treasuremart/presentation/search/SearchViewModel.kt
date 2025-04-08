package com.sielehub.treasuremart.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sielehub.treasuremart.core.Constants.Companion.PARAM_SEARCH_QUERY
import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.use_case.search.GetSearchedProductsUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SearchViewModel(
    private val getSearchedProductsUseCase: GetSearchedProductsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _searchState = mutableStateOf(SearchState())
    val searchState: State<SearchState> = _searchState

    init {
        savedStateHandle.get<String>(PARAM_SEARCH_QUERY)?.let { query ->
            searchProducts(query)
        }
    }

    fun searchProducts(query: String) {
        getSearchedProductsUseCase(query).onEach {
            when (val result = it) {
                is Resource.Success -> {
                    _searchState.value = SearchState(searchedProducts = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    _searchState.value =
                        SearchState(error = result.message ?: "Unknown error occurred")
                }

                is Resource.Loading -> {
                    _searchState.value = SearchState(isLoading = true)
                }

            }
        }.launchIn(viewModelScope)
    }

}