package com.example.rickandmorty.data.remote

import com.example.rickandmorty.data.remote.dtos.CharacterDto
import com.example.rickandmorty.data.remote.dtos.LocationDto
import com.example.rickandmorty.data.remote.dtos.OriginDto
import com.example.rickandmorty.domain.models.Character
import com.example.rickandmorty.domain.models.Location
import com.example.rickandmorty.domain.models.Origin

fun CharacterDto.toDomain() = Character(
    id = id,
    name = name,
    species = species,
    status = status,
    image = image,
    origin = origin.toDomain(),
    location = location.toDomain(),
)

fun LocationDto.toDomain() = Location(name = name)

fun OriginDto.toDomain() = Origin(name = name)