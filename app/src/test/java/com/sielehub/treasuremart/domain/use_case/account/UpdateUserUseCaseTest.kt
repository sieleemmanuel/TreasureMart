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

class UpdateUserUseCaseTest {
    private val repository: AuthRepositoryImpl = mockk()
    private lateinit var updateUserUseCase: UpdateUserUseCase

    @Before
    fun setUp() {
        updateUserUseCase = UpdateUserUseCase(repository)
    }

    @Test
    fun `updateUser should return updated user when updateUser is successful`() = runTest {
        val currentUser = fakeUser
        val updatedUser = currentUser.copy(username = "updatedUsername")
        coEvery { repository.updateUser(updatedUser) } returns updatedUser
        val result = updateUserUseCase(updatedUser).toList()
        assertThat(result[1].data?.username).isEqualTo(updatedUser)
    }
}