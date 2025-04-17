package com.sielehub.treasuremart.domain.repository

import com.sielehub.treasuremart.domain.model.SignupRequest
import com.sielehub.treasuremart.domain.model.Token
import com.sielehub.treasuremart.domain.model.User

interface AuthRepository {

    suspend fun getUser(userId: Int): User?

    suspend fun authenticateUser(username: String, password: String): Token?

    suspend fun createUser(signupRequest: SignupRequest): SignupRequest?

    suspend fun updateUser(user: User): User?

}