package com.example.dgpays.data

import com.example.dgpays.data.models.IyziReq
import com.example.dgpays.data.models.IyzicoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IyzicoApi {
    @POST("/iyzico/pay")
    suspend fun pay(@Body body: IyziReq) : Response<IyzicoResponse>
}