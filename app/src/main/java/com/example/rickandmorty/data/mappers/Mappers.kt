package com.example.rickandmorty.data.mappers

import com.example.rickandmorty.data.local.CharacterEntity
import com.example.rickandmorty.data.remote.dtos.CharacterDto
import com.example.rickandmorty.data.remote.dtos.EpisodeDto
import com.example.rickandmorty.data.remote.dtos.LocationDto
import com.example.rickandmorty.data.remote.dtos.OriginDto
import com.example.rickandmorty.domain.models.Character
import com.example.rickandmorty.domain.models.Episode
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
    episodes = episodes.joinToString(",") { it.filter { char -> char.isDigit() } }
)

fun LocationDto.toDomain() = Location(
    name = name,
    id = id.filter { char -> char.isDigit() },
    type = type,
    residents = residents?.joinToString(",") { it.filter { char -> char.isDigit() } }.toString()
)

fun OriginDto.toDomain() = Origin(name = name)

fun EpisodeDto.toDomain() = Episode(
    id = id,
    name = name,
    characters = characters,
    order = order,
    airDate = airDate,
)

fun CharacterEntity.toDomain() = Character(
    id = id,
    name = name,
    species = species,
    status = status,
    image = image,
    origin = null,
    location = null,
    episodes = null,
)

fun Character.toEntity() = CharacterEntity(
    id = id,
    name = name,
    species = species,
    status = status,
    image = image,
)