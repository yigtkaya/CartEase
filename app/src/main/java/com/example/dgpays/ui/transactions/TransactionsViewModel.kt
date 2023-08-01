package com.example.dgpays.ui.transactions

import androidx.lifecycle.ViewModel
import com.example.dgpays.main.repositories.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val repository: OrderRepository
) : ViewModel() {

    fun getAllOrders() = repository.getAllOrders()

    suspend fun updatePaidStatus(orderId: Int, paid: Boolean) = repository.updatePaidStatus(orderId, paid)

}