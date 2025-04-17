package com.sielehub.treasuremart.data.repository

import com.google.common.truth.Truth.assertThat
import com.sielehub.treasuremart.domain.network.ApiService
import com.sielehub.treasuremart.fakeNewUser
import com.sielehub.treasuremart.fakeToken
import com.sielehub.treasuremart.fakeUser
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class AuthRepositoryImplTest {
    private val apiService = mockk<ApiService>()
    private val authRepository = AuthRepositoryImpl(apiService)

    @Test
    fun `getUser should return correct user`() = runTest {
        coEvery { authRepository.getUser(1) } returns fakeUser
        val user = authRepository.getUser(1)
        assert(user == fakeUser)
    }

    @Test
    fun `authenticateUser should return correct token`() = runTest {
        coEvery {
            authRepository.authenticateUser(fakeUser.username, fakeUser.password)?.token
        } returns fakeToken
        val token = authRepository.authenticateUser(fakeUser.username, fakeUser.password)?.token
        assertThat(token).isEqualTo(fakeToken)
    }

    @Test
    fun `createUser valid user should return new user`() = runTest {
        coEvery { authRepository.createUser(fakeNewUser) } returns fakeNewUser
        val createdUser = authRepository.createUser(fakeNewUser)
        assertThat(createdUser).isEqualTo(fakeNewUser)
    }

    @Test
    fun `updateUser valid user should return updated user`() = runTest {
        coEvery { authRepository.updateUser(fakeUser) } returns fakeUser
        val updatedUser = authRepository.updateUser(fakeUser)
        assertThat(updatedUser).isEqualTo(fakeUser)
    }

}