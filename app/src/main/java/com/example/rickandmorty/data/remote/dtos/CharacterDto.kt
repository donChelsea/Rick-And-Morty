package com.example.rickandmorty.data.remote.dtos

data class CharacterDto(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val image: String,
    val origin: OriginDto,
    val location: LocationDto,
)