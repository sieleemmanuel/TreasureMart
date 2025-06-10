package com.sielehub.treasuremart.domain.use_case.orders

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteOrderUseCase(
    private val orderRepository: OrderRepository,
) {

    operator fun invoke(orderId: Long): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            val result = orderRepository.deleteOrder(orderId)
            emit(Resource.Success(data = result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
        }
    }

}