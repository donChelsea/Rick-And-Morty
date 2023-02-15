package com.example.rickandmorty.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rickandmorty.domain.models.Location
import com.example.rickandmorty.domain.models.Origin

@Entity(tableName = "character_table")
data class CharacterEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val image: String,
    val origin: Origin,
    val location: Location,
    val episodes: String,
)
