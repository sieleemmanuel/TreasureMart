package com.sielehub.treasuremart.presentation.ui.product.wish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.use_case.product.wishlist.GetWishListUseCase
import com.sielehub.treasuremart.domain.use_case.product.wishlist.RemoveFromWishListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WishListViewModel(
    private val getWishListUseCase: GetWishListUseCase,
    private val removeFromWishListUseCase: RemoveFromWishListUseCase,
) : ViewModel() {
    private val _wishListState = MutableStateFlow<WishListState>(WishListState())
    val wishListState: StateFlow<WishListState> = _wishListState.asStateFlow()

    private val _removeFromWishListState =
        MutableStateFlow<RemoveFromWishListState>(RemoveFromWishListState())
    val removeFromWishListState: StateFlow<RemoveFromWishListState> =
        _removeFromWishListState.asStateFlow()

    fun getWishList() {
        viewModelScope.launch(Dispatchers.IO) {
            getWishListUseCase().collect { result ->
                _wishListState.update {
                    when (result) {
                        is Resource.Error -> WishListState(error = result.message)
                        is Resource.Loading -> WishListState(isLoading = true)
                        is Resource.Success -> WishListState(
                            wishList = result.data ?: mutableListOf()
                        )
                    }
                }
            }
        }
    }

    fun removeFromWishList(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            removeFromWishListUseCase(productId).collect { result ->
                _removeFromWishListState.update {
                    when (result) {
                        is Resource.Error -> RemoveFromWishListState(error = result.message)
                        is Resource.Loading -> RemoveFromWishListState(isLoading = true)
                        is Resource.Success -> RemoveFromWishListState(
                            success = result.data == Unit
                        )
                    }
                }
                getWishList()
            }
        }
    }

    init {
        getWishList()
    }
}