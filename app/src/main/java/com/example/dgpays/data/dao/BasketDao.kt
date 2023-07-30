package com.example.dgpays.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.dgpays.data.models.Basket

@Dao
interface BasketDao {

    @Query("SELECT * FROM basket")
    fun getAllBasketItems() : LiveData<List<Basket>>

    @Upsert
    suspend fun upsertBasketItem(basket: Basket)

    @Query("DELETE FROM basket WHERE id = :id")
    suspend fun deleteBasketItem(id: Int)

    @Query("DELETE FROM basket")
    suspend fun deleteAllBasketItems()

    @Query("UPDATE basket SET amount = :amount WHERE id = :id")
    suspend fun updateBasketItemAmount(id: Int, amount: Int)

}