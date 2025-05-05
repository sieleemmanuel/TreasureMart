package com.sielehub.treasuremart.domain.repository

import com.sielehub.treasuremart.domain.model.LoginRequest
import com.sielehub.treasuremart.domain.model.SignupRequest
import com.sielehub.treasuremart.domain.model.SignupResponse
import com.sielehub.treasuremart.domain.model.Token
import com.sielehub.treasuremart.domain.model.User

interface AuthRepository {
    suspend fun getUsers(): List<User>

    suspend fun getUser(userId: Int): User?

    suspend fun authenticateUser(loginRequest: LoginRequest): Token?

    suspend fun createUser(signupRequest: SignupRequest): SignupResponse?

    suspend fun updateUser(user: User): User?

}