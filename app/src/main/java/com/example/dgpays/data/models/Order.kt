package com.example.dgpays.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val orderId: Int,
    val maskedCardNumber: String,
    val totalPrice: Double,
    val orderTime: Long,
    var paid: Boolean,
)