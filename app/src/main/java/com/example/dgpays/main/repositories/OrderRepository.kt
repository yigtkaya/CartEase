package com.example.dgpays.main.repositories

import com.example.dgpays.data.dao.OrderDao
import com.example.dgpays.data.models.Order
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepository @Inject constructor(
    private val OrderDao: OrderDao
){

    suspend fun updatePaidStatus(orderId: Int, paid: Boolean) = OrderDao.updatePaidStatus(orderId, paid)

    suspend fun upsertOrder(order: Order) = OrderDao.upsertOrder(order)
}