package com.example.rickandmorty.data.remote.dtos

import com.google.gson.annotations.SerializedName

data class EpisodeDto(
    val id: Int,
    val name: String,
    val characters: List<String>,
    @SerializedName("episode")
    val order: String,
    @SerializedName("air_date")
    val airDate: String,
)
