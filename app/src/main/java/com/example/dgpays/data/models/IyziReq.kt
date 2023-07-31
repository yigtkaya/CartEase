package com.example.dgpays.data.models

data class IyziReq(
    val contactName: String,
    val surName: String,
    val email: String,
    val address: String,
    val city: String,
    val country: String,
    val price: Double,
    val cardHolderName: String,
    val cardNumber: String,
    val expireMonth: String,
    val expireYear: String,
    val cvc: String
)
