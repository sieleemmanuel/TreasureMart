package com.sielehub.treasuremart.presentation.ui.product.search

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sielehub.treasuremart.core.Constants.Companion.PARAM_SEARCH_QUERY
import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.use_case.search.AddSearchHistoryUseCase
import com.sielehub.treasuremart.domain.use_case.search.ClearSearchHistoryUseCase
import com.sielehub.treasuremart.domain.use_case.search.GetSearchHistoryUseCase
import com.sielehub.treasuremart.domain.use_case.search.GetSearchSuggestionsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val getSearchSuggestionsUseCase: GetSearchSuggestionsUseCase,
    private val addSearchHistoryUseCase: AddSearchHistoryUseCase,
    private val getSearchHistoryUseCase: GetSearchHistoryUseCase,
    private val clearSearchHistoryUseCase: ClearSearchHistoryUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _searchState = mutableStateOf(SearchState())
    val searchState: State<SearchState> = _searchState

    private val _searchSuggestions = mutableStateOf(listOf<String>())
    val searchSuggestions: State<List<String>> = _searchSuggestions

    private val _searchHistory = MutableStateFlow<List<String>>(emptyList())
    val searchHistory: StateFlow<List<String>> = _searchHistory.asStateFlow()

    var currentQuery by mutableStateOf("")

    /*fun searchProducts(query: String) {
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
    }*/

    fun getSearchSuggestions(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            getSearchSuggestionsUseCase(query).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _searchSuggestions.value = result.data ?: emptyList()
                    }

                    is Resource.Error -> {
                        _searchSuggestions.value = emptyList()
                    }

                    is Resource.Loading -> {
                        _searchSuggestions.value = emptyList()
                    }
                }
            }
        }
    }

    fun addSearchToHistory(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            addSearchHistoryUseCase(query).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        // Handle success
                    }

                    is Resource.Error -> {
                        // Handle error
                    }

                    is Resource.Loading -> {
                        // Handle loading
                    }
                }
            }
        }
    }

    fun clearSearchHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            clearSearchHistoryUseCase().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        // Handle success
                    }

                    is Resource.Error -> {
                        // Handle error
                    }

                    is Resource.Loading -> {
                        // Handle loading
                    }
                }
            }
        }
    }

    fun getSearchHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            getSearchHistoryUseCase().collect { result ->
                if (result is Resource.Success) {
                    _searchHistory.update { result.data ?: emptyList() }
                } else if (result is Resource.Error) {
                    _searchHistory.update { emptyList() }
                }
            }
        }
    }

    init {
        savedStateHandle.get<String>(PARAM_SEARCH_QUERY)?.let { query ->
            //searchProducts(query)
        }
        getSearchHistory()
    }

}