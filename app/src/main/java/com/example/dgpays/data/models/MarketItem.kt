package com.example.dgpays.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "marketItems")
data class MarketItem(
    val name: String,
    val price: Double,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)
