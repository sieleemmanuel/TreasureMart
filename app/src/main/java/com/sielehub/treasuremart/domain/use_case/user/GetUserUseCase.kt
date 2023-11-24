package com.sielehub.treasuremart.domain.use_case.user

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.model.Product
import com.sielehub.treasuremart.domain.model.User
import com.sielehub.treasuremart.domain.repository.StoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUserUseCase(private val storeRepository: StoreRepository) {

    operator fun invoke(userId: Int): Flow<Resource<User?>> = storeRepository.getUser(userId)
}