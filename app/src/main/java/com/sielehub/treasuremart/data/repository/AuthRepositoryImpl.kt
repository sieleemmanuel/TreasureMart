package com.sielehub.treasuremart.data.repository

import com.sielehub.treasuremart.data.remote.StoreService
import com.sielehub.treasuremart.domain.model.User
import com.sielehub.treasuremart.domain.repository.AuthRepository

class AuthRepositoryImpl(private val storeService: StoreService) : AuthRepository {
    override suspend fun getUser(userId: Int): User? {
        return storeService.getUser(userId)?.toUser()
    }

    override suspend fun authenticateUser(username: String, password: String): String? {
        return storeService.authenticateUser(username, password)
    }

    override suspend fun createUser(user: User): Int? {
        return storeService.createUser(user)
    }

    override suspend fun updateUser(user: User): User? {
        return storeService.updateUser(user)?.toUser()
    }
}