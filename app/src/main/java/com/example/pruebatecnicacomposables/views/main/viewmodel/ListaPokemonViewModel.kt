package com.example.pruebatecnicacomposables.views.main.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.example.pruebatecnicacomposables.utils.Constants
import com.example.pruebatecnicacomposables.views.main.models.Result
import com.example.pruebatecnicajpc.connectivity.api.NetworkResponse
import com.example.pruebatecnicajpc.connectivity.api.dataAccess
import com.example.pruebatecnicajpc.connectivity.repository.ApiRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListaPokemonViewModel @Inject constructor(private val api: ApiRepository): ViewModel(){
    val listPokemon : MutableState<List<Result>> = mutableStateOf(listOf())

    fun getListPokemon(owner: LifecycleOwner)  {
        val observableResponse = dataAccess { api.simpleGet(Constants.BASE_URL +"pokemon/")}
        observableResponse.observe(owner){ response ->
            when (response.status) {
                NetworkResponse.Status.LOADING -> {
                }
                NetworkResponse.Status.SUCCESS -> {
                    response.data?.let {
                        val response = Gson().fromJson(it.string(), com.example.pruebatecnicacomposables.views.main.models.ListPokemonResponse::class.java)
                        val models = response.results
                        listPokemon.value = models
                        Log.d("data", models.toString())
                    }
                }
                NetworkResponse.Status.ERROR -> {
                    Log.d(Constants.TAG, response.toString() )
                }
            }
        }
    }

}