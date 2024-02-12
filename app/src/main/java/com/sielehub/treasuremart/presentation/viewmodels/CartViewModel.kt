package com.sielehub.treasuremart.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.sielehub.treasuremart.domain.use_case.cart.CreateCartUseCase
import com.sielehub.treasuremart.domain.use_case.cart.GetCartUseCase
import com.sielehub.treasuremart.domain.use_case.cart.GetCartsUseCase
import com.sielehub.treasuremart.domain.use_case.cart.UpdateCartUseCase

class CartViewModel(
    private val createCartUseCase: CreateCartUseCase,
    private val getCartUseCase: GetCartUseCase,
    private val getCartsUseCase: GetCartsUseCase,
    private val updateCartUseCase: UpdateCartUseCase
) : ViewModel() {
    
}