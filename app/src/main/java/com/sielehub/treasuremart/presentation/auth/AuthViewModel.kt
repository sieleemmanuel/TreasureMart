package com.sielehub.treasuremart.presentation.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.data.datastore.DataStoreManager
import com.sielehub.treasuremart.domain.model.User
import com.sielehub.treasuremart.domain.use_case.user.CreateUserUseCase
import com.sielehub.treasuremart.domain.use_case.user.GetUserUseCase
import com.sielehub.treasuremart.domain.use_case.user.LoginUseCase
import com.sielehub.treasuremart.domain.use_case.user.UpdateUserUseCase
import com.sielehub.treasuremart.presentation.user.CreateUserState
import com.sielehub.treasuremart.presentation.user.UserState
import com.sielehub.treasuremart.presentation.user.UserUpdateState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AuthViewModel(
    private val createUserUseCase: CreateUserUseCase,
    private val loginUseCase: LoginUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _userState = mutableStateOf(UserState())
    val userState: State<UserState> = _userState

    private val _createUserState = mutableStateOf(CreateUserState())
    val createUserState: State<CreateUserState> = _createUserState

    private val _userUpdateState = mutableStateOf(UserUpdateState())
    val userUpdateState: State<UserUpdateState> = _userUpdateState

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    val authToken = dataStoreManager.authToken.stateIn(
        viewModelScope,
        initialValue = runBlocking { dataStoreManager.authToken.first() },
        started = SharingStarted.WhileSubscribed()
    )

    fun createUser(user: User) {
        viewModelScope.launch {
            createUserUseCase(user).onEach {
                when (val result = it) {
                    is Resource.Success -> {
                        _createUserState.value = CreateUserState(id = result.data)
                    }

                    is Resource.Error -> {
                        _createUserState.value =
                            CreateUserState(error = result.message ?: "Unknown error occurred")
                    }

                    is Resource.Loading -> {
                        _createUserState.value = CreateUserState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun getUser(userId: Int) {
        getUserUseCase.invoke(userId).onEach {
            when (val result = it) {
                is Resource.Success -> {
                    _userState.value = UserState(user = result.data)
                }

                is Resource.Error -> {
                    _userState.value =
                        UserState(error = result.message ?: "Unknown error occurred")
                }

                is Resource.Loading -> {
                    _userState.value = UserState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun login(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            loginUseCase(username, password).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        dataStoreManager.setAuthToken(result.data ?: "")
                        _loginState.value = LoginState(token = result.data)
                    }

                    is Resource.Error -> {
                        _loginState.value =
                            LoginState(error = result.message ?: "Unknown error occurred")
                    }

                    is Resource.Loading -> {
                        _loginState.value = LoginState(isLoading = true)
                    }

                }
            }
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            updateUserUseCase(user).onEach {
                when (val result = it) {
                    is Resource.Success -> {
                        _userUpdateState.value = UserUpdateState(user = result.data)
                    }

                    is Resource.Error -> {
                        _userUpdateState.value =
                            UserUpdateState(error = result.message ?: "Unknown error occurred")
                    }

                    is Resource.Loading -> {
                        _userUpdateState.value = UserUpdateState(isLoading = true)
                    }
                }
            }

        }
    }

}