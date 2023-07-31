package com.example.dgpays.data

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface IyzicoApi {

    @POST("")
    suspend fun pay(@Query)
}