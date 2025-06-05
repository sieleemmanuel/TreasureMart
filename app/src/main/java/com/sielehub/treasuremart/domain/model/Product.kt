package com.sielehub.treasuremart.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "products_table")
@Serializable
data class Product(
    val category: String = "electronics",
    val description: String = "Expand your PS4 gaming experience, Play anywhere Fast and easy," +
            " setup Sleek design with high capacity, 3-year manufacturer's limited warranty",
    @PrimaryKey val id: Int = 12,
    val image: String = "https://fakestoreapi.com/img/61mtL65D4cL._AC_SX679_.jpg",
    val price: Double = 114.00,
    val rating: Rating = Rating(430, 4.8),
    val title: String = "WD 4TB Gaming Drive Works with Playstation 4 Portable External Hard Drive"
)

@Serializable
data class Rating(
    val count: Int,
    val rate: Double
)

@Entity(tableName = "wishes_table")
data class WishProduct(
    @PrimaryKey val productId: Int,
    @Embedded
    val product: Product,
)