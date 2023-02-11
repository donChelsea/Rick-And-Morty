package com.example.rickandmorty.domain.repository

import com.example.rickandmorty.data.remote.dtos.EpisodeDto
import com.example.rickandmorty.util.Resource
import com.example.rickandmorty.domain.models.Character
import com.example.rickandmorty.domain.models.Episode
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Path

interface ApiRepository {

    suspend fun getAllCharacters(): Flow<Resource<List<Character>>>

    suspend fun getAllEpisodes(allEpisodesString: String): Flow<Resource<List<Episode>>>

    suspend fun getSingleEpisode(@Path("id") episodeId: String): Flow<Resource<List<Episode>>>

}