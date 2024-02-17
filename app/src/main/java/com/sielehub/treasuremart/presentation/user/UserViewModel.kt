package com.sielehub.treasuremart.presentation.user

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.model.User
import com.sielehub.treasuremart.domain.use_case.user.CreateUserUseCase
import com.sielehub.treasuremart.domain.use_case.user.GetUserUseCase
import com.sielehub.treasuremart.domain.use_case.user.UpdateUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class UserViewModel(
    private val createUserUseCase: CreateUserUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel() {

    private val _userState = mutableStateOf(UserState())
    val userState: State<UserState> = _userState

    private val _createUserState = mutableStateOf(CreateUserState())
    val createUserState: State<CreateUserState> = _createUserState

    private val _userUpdateState = mutableStateOf(UserUpdateState())
    val userUpdateState: State<UserUpdateState> = _userUpdateState

    fun createUser(user: User) {
        viewModelScope.launch {
            when (val result = createUserUseCase(user)) {
                is Resource.Success -> {
                    _createUserState.value = CreateUserState(user = result.data)
                }

                is Resource.Error -> {
                    _createUserState.value =
                        CreateUserState(error = result.message ?: "Unknown error occurred")
                }

                is Resource.Loading -> {
                    _createUserState.value = CreateUserState(isLoading = true)
                }
            }
        }
    }

    fun getUser(userId: Int) {
        getUserUseCase.invoke(userId).onEach {
            when (val result = it) {
                is Resource.Success -> {
                    _userState.value = UserState(user = result.data)
                }

                is Resource.Error -> {
                    _userState.value = UserState(error = result.message ?: "Unknown error occurred")
                }

                is Resource.Loading -> {
                    _userState.value = UserState(isLoading = true)
                }
            }

        }.launchIn(viewModelScope)

    }

    fun updateUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = updateUserUseCase(user)) {
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