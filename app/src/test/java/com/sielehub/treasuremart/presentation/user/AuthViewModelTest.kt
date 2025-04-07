package com.sielehub.treasuremart.presentation.user

import com.sielehub.treasuremart.domain.repository.StoreRepository
import com.sielehub.treasuremart.domain.use_case.user.CreateUserUseCase
import com.sielehub.treasuremart.domain.use_case.user.GetUserUseCase
import com.sielehub.treasuremart.domain.use_case.user.UpdateUserUseCase
import com.sielehub.treasuremart.presentation.auth.AuthViewModel
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
    private val repository: StoreRepository = mockk()
    private val createUserUseCase = CreateUserUseCase(repository)
    private val userUseCase = GetUserUseCase(repository)
    private val updaterUserUseCase = UpdateUserUseCase(repository)

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: AuthViewModel

    @Before
    fun setUp() {
        viewModel = AuthViewModel(createUserUseCase, userUseCase, updaterUserUseCase)
    }

    @After
    fun tearDown() {
    }
}