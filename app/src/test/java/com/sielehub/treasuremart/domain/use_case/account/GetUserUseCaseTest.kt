package com.sielehub.treasuremart.domain.use_case.account

import com.google.common.truth.Truth.assertThat
import com.sielehub.treasuremart.data.repository.AuthRepositoryImpl
import com.sielehub.treasuremart.fakeUser
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetUserUseCaseTest {
    private val repository: AuthRepositoryImpl = mockk()
    private lateinit var getUserUseCase: GetUserUseCase

    @Before
    fun setUp() {
        getUserUseCase = GetUserUseCase(repository)
    }

    @Test
    fun `getUser should return user when getUser is successful`() = runTest {
        val expectedUser = fakeUser
        coEvery { repository.getUser(1) } returns expectedUser
        val result = getUserUseCase(1).toList()
        assertThat(result[1].data).isEqualTo(expectedUser)
    }
}