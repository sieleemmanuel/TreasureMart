package com.sielehub.treasuremart.presentation.account

import com.sielehub.treasuremart.data.repository.AuthRepositoryImpl
import com.sielehub.treasuremart.domain.use_case.account.CreateUserUseCase
import com.sielehub.treasuremart.domain.use_case.account.GetUserUseCase
import com.sielehub.treasuremart.domain.use_case.account.UpdateUserUseCase
import com.sielehub.treasuremart.presentation.ui.auth.AuthViewModel
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AuthViewModelTest {
    @MockK
    private val repository: AuthRepositoryImpl = mockk()
    private val createUserUseCase = CreateUserUseCase(repository)
    private val userUseCase = GetUserUseCase(repository)
    private val updaterUserUseCase = UpdateUserUseCase(repository)

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: AuthViewModel

    @Before
    fun setUp() {
        // viewModel = AuthViewModel(createUserUseCase, userUseCase, updaterUserUseCase)
    }

    @After
    fun tearDown() {
    }
}