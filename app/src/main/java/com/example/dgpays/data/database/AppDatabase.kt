package com.example.dgpays.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dgpays.data.dao.BasketDao
import com.example.dgpays.data.dao.MarketItemDao
import com.example.dgpays.data.dao.OrderDao
import com.example.dgpays.data.models.Basket
import com.example.dgpays.data.models.MarketItem
import com.example.dgpays.data.models.Order

@Database(
    entities =
    [
        MarketItem::class,
        Basket::class,
        Order::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun marketItemDao(): MarketItemDao
    abstract fun basketDao(): BasketDao

    abstract fun orderDao(): OrderDao
}