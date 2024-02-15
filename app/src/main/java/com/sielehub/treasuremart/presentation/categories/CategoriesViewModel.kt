package com.sielehub.treasuremart.presentation.categories

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.use_case.categories.GetCategoriesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CategoriesViewModel(private val getCategoriesUseCase: GetCategoriesUseCase) : ViewModel() {
    private val _categoriesState = mutableStateOf(CategoryListState())
    val categoriesState: State<CategoryListState> = _categoriesState

    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            getCategoriesUseCase().onEach {
                when (it) {

                    is Resource.Loading -> {
                        _categoriesState.value = CategoryListState(isLoading = true)
                    }

                    is Resource.Success -> {
                        _categoriesState.value = CategoryListState(
                            categories = it.data ?: emptyList()
                        )
                    }

                    is Resource.Error -> {
                        _categoriesState.value = CategoryListState(
                            error = it.message ?: "Unknown error occurred"
                        )
                    }
                }

            }.launchIn(this)
        }
    }

}