package com.sielehub.treasuremart.presentation.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.data.datastore.DataStoreManager
import com.sielehub.treasuremart.domain.use_case.account.LogoutUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AccountViewModel(
    private val logoutUseCase: LogoutUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _logoutState = MutableStateFlow(LogoutState())
    val logoutState: StateFlow<LogoutState> get() = _logoutState.asStateFlow()

    val authToken = dataStoreManager.authToken.stateIn(
        viewModelScope,
        initialValue = runBlocking { dataStoreManager.authToken.first() },
        started = SharingStarted.Eagerly
    )

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            logoutUseCase.invoke().onEach {
                when (val result = it) {

                    is Resource.Loading -> {
                        _logoutState.value = LogoutState(isLoading = true)
                    }

                    is Resource.Success -> {
                        _logoutState.value = LogoutState(isLoggedOut = true)
                    }

                    is Resource.Error -> {
                        _logoutState.value =
                            LogoutState(error = result.message ?: "Unknown error occurred")
                    }

                }
            }.launchIn(viewModelScope)
        }
    }

    companion object {
        private const val TAG = "AccountViewModel"
    }
}