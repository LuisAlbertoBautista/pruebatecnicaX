package com.example.pruebatecnicajpc.connectivity.repository


import com.example.pruebatecnicajpc.connectivity.api.ApiResponse
import com.example.pruebatecnicajpc.connectivity.api.ConnectionModel
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val connectionModel: ConnectionModel
): ApiResponse() {
    suspend fun simpleGet(url: String) = getResult { connectionModel.simpleGet(url) }

}