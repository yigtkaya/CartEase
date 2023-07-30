package com.example.dgpays.main.repositories

import com.example.dgpays.data.dao.MarketItemDao
import com.example.dgpays.data.models.MarketItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarketItemRepository @Inject constructor(
    private val marketItemDao: MarketItemDao
) {

    fun getAllMarketItems() = marketItemDao.getAllMarketItems()

    suspend fun upsertMarketItem(marketItem: MarketItem) = marketItemDao.upsertMarketItem(marketItem)



}