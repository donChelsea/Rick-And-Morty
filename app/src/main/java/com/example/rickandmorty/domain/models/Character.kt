package com.example.rickandmorty.domain.models

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val image: String,
    val origin: Origin,
    val location: Location,
)