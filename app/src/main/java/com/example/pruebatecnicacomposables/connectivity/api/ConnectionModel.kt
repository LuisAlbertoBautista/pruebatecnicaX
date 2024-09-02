package com.example.pruebatecnicajpc.connectivity.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ConnectionModel {
    @GET
    @Headers("Content-Type: application/json; charset=UTF-8")
    suspend fun simpleGet(@Url url: String): Response<ResponseBody>
}