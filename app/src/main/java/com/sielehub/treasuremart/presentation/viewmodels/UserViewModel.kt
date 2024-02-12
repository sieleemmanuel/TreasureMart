package com.sielehub.treasuremart.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sielehub.treasuremart.domain.model.User
import com.sielehub.treasuremart.domain.use_case.user.CreateUserUseCase
import com.sielehub.treasuremart.domain.use_case.user.GetUserUseCase
import com.sielehub.treasuremart.domain.use_case.user.UpdateUserUseCase
import kotlinx.coroutines.launch

class UserViewModel(
    private val createUserUseCase: CreateUserUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel() {

    private val _user = mutableStateOf(null)
    val user: State<User?> get() = _user

    fun createUser(user: User) {
        viewModelScope.launch {
            createUserUseCase.invoke(user)
        }
    }

    fun getUser(userId: Int) {
        viewModelScope.launch {
            getUserUseCase.invoke(userId)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            updateUserUseCase.invoke(user)
        }
    }

}