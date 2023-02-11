package com.example.rickandmorty.data.remote.dtos

import com.google.gson.annotations.SerializedName

data class CharacterDto(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val image: String,
    val origin: OriginDto,
    val location: LocationDto,
    @SerializedName("episode")
    val episodes: List<String>,
)