package com.example.dgpays.main.repositories

import com.example.dgpays.data.IyzicoApi
import com.example.dgpays.data.models.IyziReq
import com.example.dgpays.data.models.IyzicoResponse
import retrofit2.Response
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val api: IyzicoApi
) {

    suspend fun pay(body: IyziReq) : Response<IyzicoResponse> {
        return api.pay(body)
    }
}