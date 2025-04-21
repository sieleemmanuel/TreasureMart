package com.sielehub.treasuremart.presentation.ui.notifications

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.use_case.notifications.GetNotificationsUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class NotificationsViewModel(
    private val getNotificationsUseCase: GetNotificationsUseCase,
) : ViewModel() {

    private val _notificationState = mutableStateOf(NotificationsState())
    val notificationsState: State<NotificationsState> = _notificationState

    fun searchProducts(query: String) {
        getNotificationsUseCase().onEach {
            when (val result = it) {
                is Resource.Success -> {
                    _notificationState.value =
                        NotificationsState(notifications = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    _notificationState.value =
                        NotificationsState(error = result.message ?: "Unknown error occurred")
                }

                is Resource.Loading -> {
                    _notificationState.value = NotificationsState(isLoading = true)
                }

            }
        }.launchIn(viewModelScope)
    }

}