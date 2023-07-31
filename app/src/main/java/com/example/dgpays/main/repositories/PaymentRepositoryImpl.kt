package com.example.dgpays.main.repositories

import com.example.dgpays.data.IyzicoApi
import com.example.dgpays.data.models.IyzicoResponse
import com.example.dgpays.main.utils.Resource
import java.lang.Exception
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val api: IyzicoApi
) : PaymentRepository{

    override suspend fun pay(body: Map<String, Any>) : Resource<IyzicoResponse> {

       return try {
            val response = api.pay(body)
            val result = response.body()

            if (response.isSuccessful && result != null && result.status == "success") {
                Resource.Success(result)
            } else {
                Resource.Error(result?.errorMessage.toString() ?: "Error Occurred")
            }
        } catch (e: Exception) {
            Resource.Error(e.message.toString() ?: "Error Occurred")
        }
    }
}