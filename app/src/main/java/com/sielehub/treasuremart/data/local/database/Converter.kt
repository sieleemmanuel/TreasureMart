package com.sielehub.treasuremart.data.local.database

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.sielehub.treasuremart.domain.model.Address
import com.sielehub.treasuremart.domain.model.CartProduct
import com.sielehub.treasuremart.domain.model.Rating
import com.sielehub.treasuremart.presentation.util.jsonToModel
import com.sielehub.treasuremart.presentation.util.modelToJsonArray

@TypeConverters
class Converter {
    @TypeConverter
    fun ratingToString(rating: Rating): String = rating.modelToJsonArray()

    @TypeConverter
    fun stringToRating(string: String): Rating = string.jsonToModel()

    @TypeConverter
    fun cartProductToString(cartProduct: CartProduct): String = cartProduct.modelToJsonArray()

    @TypeConverter
    fun stringToCartProduct(string: String): CartProduct = string.jsonToModel()

    @TypeConverter
    fun cartProductsToString(cartProducts: List<CartProduct>): String =
        cartProducts.modelToJsonArray()

    @TypeConverter
    fun stringToCartProducts(string: String): List<CartProduct> = string.jsonToModel()

    @TypeConverter
    fun addressToString(address: Address): String = address.modelToJsonArray()

    @TypeConverter
    fun stringToAddress(string: String): Address = string.jsonToModel()
}