package com.example.dgpays.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val orderId: Int = 0,
    val maskedCardNumber: String,
    val totalPrice: Double,
    val orderTime: String,
    var paid: Boolean,
)