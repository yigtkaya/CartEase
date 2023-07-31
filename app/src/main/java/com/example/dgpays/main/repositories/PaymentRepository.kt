package com.example.dgpays.main.repositories

import com.example.dgpays.data.models.IyzicoResponse
import com.example.dgpays.main.utils.Resource


interface PaymentRepository {
    suspend fun pay(body: Map<String, Any>) : Resource<IyzicoResponse>
}