package com.example.pruebatecnicacomposables.views.detail.models

data class Pokemon(
    val abilities: List<Ability> = listOf(),
    val base_experience: Int = 0,
    val forms: List<Form> = listOf(),
    val height: Int = 0,
    val held_items: List<Any> = listOf(),
    val id: Int = 0,
    val is_default: Boolean = false,
    val location_area_encounters: String = "",
    val name: String = "",
    val order: Int = 0,
    val past_abilities: List<Any> = listOf(),
    val past_types: List<Any> = listOf(),
    val species: Species = Species(),
    val sprites: Sprites = Sprites(),
    val types: List<Type> = listOf(),
    val weight: Int = 0
)