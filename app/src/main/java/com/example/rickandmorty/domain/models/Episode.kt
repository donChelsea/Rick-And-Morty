package com.example.rickandmorty.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Episode(
    val id: Int,
    val name: String,
    val characters: List<String>,
    val order: String,
    val airDate: String,
) : Parcelable