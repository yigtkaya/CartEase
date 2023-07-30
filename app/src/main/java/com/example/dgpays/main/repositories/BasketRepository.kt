package com.example.dgpays.main.repositories

import com.example.dgpays.data.dao.BasketDao
import com.example.dgpays.data.models.Basket
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BasketRepository @Inject constructor(
    private val basketDao: BasketDao
){

        fun getAllBasketItems() = basketDao.getAllBasketItems()

        suspend fun updateBasketItemAmount(id: Int, amount: Int) = basketDao.updateBasketItemAmount(id, amount)

        suspend fun deleteBasketItem(id: Int) = basketDao.deleteBasketItem(id)

        suspend fun deleteAllBasketItems() = basketDao.deleteAllBasketItems()

        suspend fun upsertBasketItem(basket: Basket) = basketDao.upsertBasketItem(basket)
}