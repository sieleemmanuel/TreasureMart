package com.sielehub.treasuremart.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sielehub.treasuremart.domain.model.Cart
import com.sielehub.treasuremart.domain.model.Order
import com.sielehub.treasuremart.domain.model.Product
import com.sielehub.treasuremart.domain.model.WishProduct

@Database(
    entities = [
        Product::class,
        WishProduct::class,
        Cart::class,
        Order::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converter::class)
abstract class StoreDb : RoomDatabase() {
    abstract val storeDao: StoreDao
    abstract val wishDao: WishDao
    abstract val cartDao: CartDao
    abstract val ordersDao: OrdersDao

    companion object {
        const val DATABASE_NAME = "store_db"
        fun getInstance(context: Context): StoreDb {
            return Room.databaseBuilder(
                context,
                StoreDb::class.java,
                DATABASE_NAME
            ).build()
        }
    }
    
}