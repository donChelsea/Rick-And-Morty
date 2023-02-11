package com.example.rickandmorty.data.remote.dtos

import com.google.gson.annotations.SerializedName

data class LocationDto(
    val name: String,
    @SerializedName("url")
    val id: String,
    val type: String?,
    val residents: List<String>?,
)