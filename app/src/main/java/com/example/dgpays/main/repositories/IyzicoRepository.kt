package com.example.dgpays.main.repositories

interface IyzicoRepository {

    suspend fun pay() : IyzicoResponse
}