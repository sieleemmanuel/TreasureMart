package com.sielehub.treasuremart.domain.repository

import com.sielehub.treasuremart.domain.model.User

interface AuthRepository {

    suspend fun getUser(userId: Int): User?

    suspend fun authenticateUser(username: String, password: String): String?

    suspend fun createUser(user: User): Int?

    suspend fun updateUser(user: User): User?

}