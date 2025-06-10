package com.sielehub.treasuremart.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "orders_table")
@Serializable
data class Order(
    @PrimaryKey(autoGenerate = false) val orderId: Long = System.currentTimeMillis(),
    val address: Address,
    val orderItems: List<CartProduct>,
    val orderTotal: Double,
    val orderDate: String,
    val orderStatus: String
)
