package com.example.rickandmorty.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val name: String,
    val id: String,
    val type: String?,
    val residents: String,
) : Parcelable