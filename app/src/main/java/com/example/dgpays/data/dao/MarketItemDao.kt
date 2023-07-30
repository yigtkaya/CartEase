package com.example.dgpays.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.dgpays.data.models.MarketItem

@Dao
interface MarketItemDao {

    @Upsert
    suspend fun upsertMarketItem(item: MarketItem)

    @Query("SELECT * FROM marketItems ORDER BY id DESC")
    fun getAllMarketItems() : LiveData<List<MarketItem>>

}