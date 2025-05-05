package com.sielehub.treasuremart.data.repository

import com.sielehub.treasuremart.domain.model.LoginRequest
import com.sielehub.treasuremart.domain.model.SignupRequest
import com.sielehub.treasuremart.domain.model.SignupResponse
import com.sielehub.treasuremart.domain.model.Token
import com.sielehub.treasuremart.domain.model.User
import com.sielehub.treasuremart.domain.network.ApiService
import com.sielehub.treasuremart.domain.repository.AuthRepository

class AuthRepositoryImpl(private val apiService: ApiService) : AuthRepository {
    override suspend fun getUsers(): List<User> {
        return apiService.getUsers().map { it.toUser() }
    }

    override suspend fun getUser(userId: Int): User? {
        return apiService.getUser(userId)?.toUser()
    }

    override suspend fun authenticateUser(loginRequest: LoginRequest): Token? {
        return apiService.authenticateUser(loginRequest)

    }

    override suspend fun createUser(signupRequest: SignupRequest): SignupResponse {
        return apiService.createUser(signupRequest)
    }

    override suspend fun updateUser(user: User): User? = apiService.updateUser(user)?.toUser()

}