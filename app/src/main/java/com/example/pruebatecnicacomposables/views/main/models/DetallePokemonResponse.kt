package com.example.pruebatecnicacomposables.views.main.models

import com.example.pruebatecnicacomposables.views.main.models.Ability

data class DetallePokemonResponse(
    val abilities: List<com.example.pruebatecnicacomposables.views.main.models.Ability>,
    val base_experience: Int,
    val forms: List<com.example.pruebatecnicacomposables.views.main.models.Form>,
    val game_indices: List<com.example.pruebatecnicacomposables.views.main.models.GameIndice>,
    val height: Int,
    val held_items: List<Any>,
    val id: Int,
    val is_default: Boolean,
    val location_area_encounters: String,
    val moves: List<com.example.pruebatecnicacomposables.views.main.models.Move>,
    val name: String,
    val order: Int,
    val past_types: List<Any>,
    val species: com.example.pruebatecnicacomposables.views.main.models.Species,
    val sprites: com.example.pruebatecnicacomposables.views.main.models.Sprites,
    val stats: List<com.example.pruebatecnicacomposables.views.main.models.Stat>,
    val types: List<com.example.pruebatecnicacomposables.views.main.models.Type>,
    val weight: Int
)