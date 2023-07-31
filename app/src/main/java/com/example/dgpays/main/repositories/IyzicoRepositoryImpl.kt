package com.example.dgpays.main.repositories

import com.example.dgpays.data.IyzicoApi
import java.lang.Exception
import javax.inject.Inject

class IyzicoRepositoryImpl @Inject constructor(
    private val api: IyzicoApi
) {

    override suspend fun pay() : IyzicoResponse {
        return try {

        } catch (e: Exception) {

        }
    }
}