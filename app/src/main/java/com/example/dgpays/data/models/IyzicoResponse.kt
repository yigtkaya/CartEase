package com.example.dgpays.data.models

data class IyzicoResponse(
    val status: String,
    var errorMessage: String?,
    var price: Double?,
    var systemTime: String,
    var paymentId: String?,

    )
