package com.sielehub.treasuremart.presentation.dashboard

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _superDealsProductsState =
        mutableStateOf<SuperDealsProductsState>(SuperDealsProductsState())
    val superDealsProductsState: State<SuperDealsProductsState> = _superDealsProductsState

    private val _bestPickProductsState =
        mutableStateOf<BestPickProductsState>(BestPickProductsState())
    val bestPickProductsState: State<BestPickProductsState> = _bestPickProductsState


}