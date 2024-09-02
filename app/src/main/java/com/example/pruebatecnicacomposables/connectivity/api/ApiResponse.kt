package com.example.pruebatecnicajpc.connectivity.api

import org.json.JSONObject
import retrofit2.Response

abstract class ApiResponse {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): NetworkResponse<T> {
        try {
            val response = call()
            val headers = response.headers()
            if (response.isSuccessful) {
                val body = response.body()
                return if (body != null)
                    NetworkResponse.success(response.code(), body, headers)
                else{
                    NetworkResponse.success(response.code(), JSONObject() as T, headers)
                }

            }

            val error = if (response.errorBody() == null) JSONObject() else convertToObject(response.errorBody()?.string())
            return error(response.code()," ${response.code()} ${response.message()}", error)
        } catch (e: Exception) {
            return error(-1,e.message ?: e.toString(), JSONObject())
        }
    }

    private fun <T> error(code: Int, message: String, error: JSONObject): NetworkResponse<T> {
        return NetworkResponse.error(
            code,
            "Network call has failed for a following reason: $message",
            null,
            error
        )
    }
}