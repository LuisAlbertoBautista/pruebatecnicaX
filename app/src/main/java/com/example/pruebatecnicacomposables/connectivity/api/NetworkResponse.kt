package com.example.pruebatecnicajpc.connectivity.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONObject

data class NetworkResponse<out T>(val code: Int, val status: Status, val data: T?, val message: String?, val error: JSONObject?, val headers: Headers?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    enum class MetadataError {
        SUCCESS,
        ERROR,
        STOP
    }

    companion object {
        fun <T> success(code: Int, data: T, headers: Headers?): NetworkResponse<T> {
            return NetworkResponse(code, Status.SUCCESS, data, null,null, headers)
        }

        fun <T> error(code: Int, msg: String, data: T?, error: JSONObject?): NetworkResponse<T> {
            return NetworkResponse(code, Status.ERROR, data, msg, error, null)
        }

        fun <T> loading(data: T?): NetworkResponse<T> {
            return NetworkResponse(0, Status.LOADING, data, null,null, null)
        }
    }
}

fun <T> dataAccess(networkCall: suspend () -> NetworkResponse<T>): LiveData<NetworkResponse<T>> = liveData(
    Dispatchers.IO) {
    emit(NetworkResponse.loading(null))

    val responseStatus = networkCall.invoke()
    if (responseStatus.status == NetworkResponse.Status.SUCCESS) {
        emit(
            NetworkResponse.success(
                responseStatus.code,
                responseStatus.data,
                responseStatus.headers
            ) as NetworkResponse<T>
        )

    } else if (responseStatus.status == NetworkResponse.Status.ERROR) {
        emit(
            NetworkResponse.error(
                responseStatus.code,
                responseStatus.message!!,
                responseStatus.data,
                responseStatus.error
            )
        )
    }
}

fun convertToObject(data: String?): JSONObject{
    var json = JSONObject("{success:false, msg:'no es un formato JSON'}")

    if (data?.startsWith("{") == true){
        json = JSONObject(data)
    }else if (data?.startsWith("[") == true){
        val jsonArray = JSONObject()
        jsonArray.put("data", JSONArray(data))
        json = jsonArray
    }

    return json

}

fun metadataError(obj: JSONObject): NetworkResponse.MetadataError {
    if (obj.has("metadata")){
        val metaData = obj.getJSONObject("metadata")
        return if (metaData.has("is_error")){
            if (metaData.getBoolean("is_error")) NetworkResponse.MetadataError.ERROR else NetworkResponse.MetadataError.SUCCESS
        } else{
            NetworkResponse.MetadataError.STOP
        }
    }
    return NetworkResponse.MetadataError.STOP
}
