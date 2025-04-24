package com.sielehub.treasuremart.domain.use_case.account

import com.google.common.truth.Truth.assertThat
import com.sielehub.treasuremart.data.repository.AuthRepositoryImpl
import com.sielehub.treasuremart.fakeNewUser
import com.sielehub.treasuremart.fakeSignupResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CreateUserUseCaseTest {
    private val repository: AuthRepositoryImpl = mockk()
    private lateinit var createUserUseCase: CreateUserUseCase

    @Before
    fun setUp() {
        createUserUseCase = CreateUserUseCase(repository)
    }

    @Test
    fun `invoke should return user when createUser is successful`() = runTest {
        val newUser = fakeNewUser
        val expectedResult = fakeSignupResponse
        coEvery { repository.createUser(newUser) } returns expectedResult
        val result = createUserUseCase(newUser).toList()
        assertThat(result[1].data).isEqualTo(expectedResult)
    }

    @Test
    fun `createUser should return error when createUser fails`() = runTest {
        val newUser = fakeNewUser
        val errorMessage = "An error occurred"
        coEvery { repository.createUser(newUser) } throws Exception(errorMessage)
        val result = createUserUseCase(newUser).toList()
        assertThat(result[1].message).isEqualTo(errorMessage)
    }
}