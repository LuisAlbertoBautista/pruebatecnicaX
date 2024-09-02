package com.example.pruebatecnicacomposables.views.main.models

import java.io.Serializable

data class ListPokemonResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<com.example.pruebatecnicacomposables.views.main.models.Result>
):Serializable