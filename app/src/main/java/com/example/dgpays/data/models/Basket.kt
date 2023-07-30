package com.example.dgpays.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Basket(
    val name: String,
    val price: Double,
    val amount: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)
