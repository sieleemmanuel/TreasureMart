package com.sielehub.treasuremart.domain.use_case.orders

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.domain.model.Order
import com.sielehub.treasuremart.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetOrdersUseCase(
    private val orderRepository: OrderRepository,
) {

    operator fun invoke(): Flow<Resource<List<Order>>> = flow {
        emit(Resource.Loading())
        try {
            orderRepository.getOrders().collect {
                emit(Resource.Success(data = it))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
        }
    }

}