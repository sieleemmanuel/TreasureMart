package com.sielehub.treasuremart.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "cart_table")
@Serializable
data class Cart(
    val date: String,
    @PrimaryKey val id: Int,
    val products: List<CartProduct>,
    val userId: Int
)

@Serializable
data class CartProduct(
    val productId: Int,
    var quantity: Int,
    var price: Double? = null,
    var title: String? = null,
    var image: String? = null,
    var category: String? = null,
    var description: String? = null,
    var isSelected: Boolean = true
)