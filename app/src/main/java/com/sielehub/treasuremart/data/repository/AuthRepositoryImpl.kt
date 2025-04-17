package com.sielehub.treasuremart.data.repository

import com.sielehub.treasuremart.domain.model.SignupRequest
import com.sielehub.treasuremart.domain.model.Token
import com.sielehub.treasuremart.domain.model.User
import com.sielehub.treasuremart.domain.network.ApiService
import com.sielehub.treasuremart.domain.repository.AuthRepository

class AuthRepositoryImpl(private val apiService: ApiService) : AuthRepository {
    override suspend fun getUser(userId: Int): User? {
        return apiService.getUser(userId)?.toUser()
    }

    override suspend fun authenticateUser(username: String, password: String): Token? {
        return apiService.authenticateUser(username, password)

    }

    override suspend fun createUser(signupRequest: SignupRequest): SignupRequest? {
        return apiService.createUser(signupRequest)
    }

    override suspend fun updateUser(user: User): User? = apiService.updateUser(user)?.toUser()

}