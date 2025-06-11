package com.sielehub.treasuremart.data.repository

import com.sielehub.treasuremart.data.datastore.DataStoreManager
import com.sielehub.treasuremart.data.local.database.CartDao
import com.sielehub.treasuremart.data.local.database.OrdersDao
import com.sielehub.treasuremart.domain.model.Order
import com.sielehub.treasuremart.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class OrderRepositoryImpl(
    private val ordersDao: OrdersDao,
    private val cartDao: CartDao,
    private val dataStoreManager: DataStoreManager
) : OrderRepository {
    override suspend fun createOrder(order: Order): Long? {
        val isSuccessful = ordersDao.insertOrder(order) > 0
        if (isSuccessful) {
            val currentCart = cartDao.getCart(dataStoreManager.currentUserId.first()).first()
            val cartProducts = currentCart?.products?.toMutableList()
            order.orderItems.forEach { orderItem ->
                val product = cartProducts?.find { it.productId == orderItem.productId }
                cartProducts?.remove(product)
            }
            val updatedCart = currentCart?.copy(products = cartProducts ?: emptyList())
            updatedCart?.let { cartDao.updateCart(it.id, it.products) }
            return ordersDao.getOrder(order.orderId).first()?.orderId
        }
        return null
    }

    override suspend fun updateOrder(order: Order): Boolean {
        return ordersDao.updateOrder(order.orderId, order.orderItems) > 0
    }

    override fun getOrders(): Flow<List<Order>> {
        return ordersDao.getOrders()
    }

    override fun getOrder(orderId: Long): Flow<Order?> {
        return ordersDao.getOrder(orderId)
    }

    override suspend fun deleteOrder(orderId: Long): Boolean {
        return ordersDao.removeOrder(orderId) > 0
    }

}