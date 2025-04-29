package com.sielehub.treasuremart.presentation.ui.checkout

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.sielehub.treasuremart.domain.use_case.categories.GetCategoriesUseCase
import com.sielehub.treasuremart.presentation.ui.product.categories.CategoryListState

class CheckoutViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _categoriesState = mutableStateOf(CategoryListState())
    val categoriesState: State<CategoryListState> = _categoriesState

}