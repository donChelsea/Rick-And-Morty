package com.example.rickandmorty.domain.repository

import com.example.rickandmorty.data.remote.dtos.CharacterDto
import com.example.rickandmorty.domain.models.Character
import com.example.rickandmorty.domain.models.Episode
import com.example.rickandmorty.domain.models.Location
import com.example.rickandmorty.util.Resource
import kotlinx.coroutines.flow.Flow

interface ApiRepository {

    suspend fun getAllCharacters(): Flow<Resource<List<Character>>>

    suspend fun getMultipleCharacters(charactersString: String): Flow<Resource<List<Character>>>

    suspend fun getMultipleEpisodes(allEpisodesString: String): Flow<Resource<List<Episode>>>

    suspend fun getSingleEpisode(episodeId: String): Flow<Resource<List<Episode>>>

    suspend fun getSingleLocation(locationId: String): Flow<Resource<Location>>

}