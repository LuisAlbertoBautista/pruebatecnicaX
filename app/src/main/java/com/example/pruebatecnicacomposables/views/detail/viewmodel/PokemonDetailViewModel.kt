package com.example.pruebatecnicacomposables.views.detail.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pruebatecnicacomposables.utils.Constants
import com.example.pruebatecnicacomposables.utils.Constants.BASE_URL
import com.example.pruebatecnicacomposables.views.detail.models.Pokemon
import com.example.pruebatecnicacomposables.views.main.models.Result
import com.example.pruebatecnicajpc.connectivity.api.NetworkResponse
import com.example.pruebatecnicajpc.connectivity.api.dataAccess
import com.example.pruebatecnicajpc.connectivity.repository.ApiRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(private val api: ApiRepository): ViewModel(){
    val pokemon : MutableState<Pokemon> = mutableStateOf(Pokemon())

    fun getDetailPokemon(owner: LifecycleOwner, id: Int)  {
        val observableResponse = dataAccess { api.simpleGet("${BASE_URL}pokemon/$id/")}
        observableResponse.observe(owner){ response ->
            when (response.status) {
                NetworkResponse.Status.LOADING -> {
                }
                NetworkResponse.Status.SUCCESS -> {
                    response.data?.let {
                        val response = Gson().fromJson(it.string(), Pokemon::class.java)
                        val models = response
                        pokemon.value = models
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