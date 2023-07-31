package com.example.dgpays.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.dgpays.data.models.Order

@Dao
interface OrderDao {

    @Upsert
    suspend fun upsertOrder(order: Order)

    // create a query to update the paid status of the order
    @Query("UPDATE orders SET paid = :paid WHERE orderId = :orderId")
    suspend fun updatePaidStatus(orderId: Int, paid: Boolean)

}