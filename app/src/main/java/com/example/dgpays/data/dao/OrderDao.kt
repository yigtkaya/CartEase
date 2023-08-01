package com.example.dgpays.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.dgpays.data.models.Order

@Dao
interface OrderDao {

    @Upsert
    suspend fun upsertOrder(order: Order)
    @Query("UPDATE orders SET paid = :paid WHERE orderId = :orderId")
    suspend fun updatePaidStatus(orderId: Int, paid: Boolean)

    @Query("SELECT * FROM orders")
    fun getAllOrders(): LiveData<List<Order>>
}