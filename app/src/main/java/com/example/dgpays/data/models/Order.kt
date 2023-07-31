package com.example.dgpays.data.models

import java.util.Date

data class Order(
    val orderId: Int,
    val maskedCardNumber: String,
    val totalPrice: Double,
    val orderTime: Date,

)