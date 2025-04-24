package com.sielehub.treasuremart.presentation.ui.auth

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.data.datastore.DataStoreManager
import com.sielehub.treasuremart.domain.model.SignupRequest
import com.sielehub.treasuremart.domain.model.User
import com.sielehub.treasuremart.domain.use_case.account.CreateUserUseCase
import com.sielehub.treasuremart.domain.use_case.account.GetUserUseCase
import com.sielehub.treasuremart.domain.use_case.account.LoginUseCase
import com.sielehub.treasuremart.domain.use_case.account.UpdateUserUseCase
import com.sielehub.treasuremart.presentation.ui.account.UserState
import com.sielehub.treasuremart.presentation.ui.account.UserUpdateState
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

class AuthViewModel(
    private val createUserUseCase: CreateUserUseCase,
    private val loginUseCase: LoginUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _userState = mutableStateOf(UserState())
    val userState: State<UserState> = _userState

    private val _signupState = mutableStateOf(SignupState())
    val signupState: State<SignupState> = _signupState

    private val _userUpdateState = mutableStateOf(UserUpdateState())
    val userUpdateState: State<UserUpdateState> = _userUpdateState

    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> get() = _loginState.asStateFlow()

    val authToken = dataStoreManager.authToken.stateIn(
        viewModelScope,
        initialValue = runBlocking { dataStoreManager.authToken.first() },
        started = SharingStarted.Eagerly
    )

    fun createUser(signupRequest: SignupRequest) {
        viewModelScope.launch {
            createUserUseCase(signupRequest).onEach {
                when (val result = it) {
                    is Resource.Success -> {
                        _signupState.value = SignupState(signupResponse = result.data)
                    }

                    is Resource.Error -> {
                        Log.d(TAG, "createUser error: ${result.message}")
                        _signupState.value =
                            SignupState(error = result.message ?: "Unknown error occurred")
                    }

                    is Resource.Loading -> {
                        _signupState.value = SignupState(isLoading = true)
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
                Log.d(TAG, "login state: ${result.data}")
                when (result) {
                    is Resource.Loading -> {
                        _loginState.value = LoginState(isLoading = true)
                    }

                    is Resource.Success -> {
                        dataStoreManager.setAuthToken(result.data?.token ?: "")
                        _loginState.value = LoginState(token = result.data?.token)
                    }

                    is Resource.Error -> {
                        Log.d(TAG, "login error: ${result.message}")
                        _loginState.value =
                            LoginState(error = result.message ?: "Unknown error occurred")
                    }
                }
            }.launchIn(this)
        }
    }

    fun logout() {
        viewModelScope.launch {
            dataStoreManager.setAuthToken("")
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

    companion object {
        private const val TAG = "AuthViewModel"
    }
}