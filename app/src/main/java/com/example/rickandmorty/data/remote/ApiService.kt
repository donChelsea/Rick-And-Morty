package com.example.rickandmorty.data.remote

import com.example.rickandmorty.data.remote.dtos.CharacterDto
import com.example.rickandmorty.data.remote.dtos.EpisodeDto
import com.example.rickandmorty.data.remote.dtos.ListResultDto
import com.example.rickandmorty.data.remote.dtos.LocationDto
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {

    @GET("character")
    suspend fun getAllCharacters(): ListResultDto<CharacterDto>
    @GET("character/{id}")
    suspend fun getMultipleCharacters(@Path("id") id: String): List<CharacterDto>

    @GET("episode/{id}")
    suspend fun getMultipleEpisodes(@Path("id") id: String): List<EpisodeDto>

    @GET("episode/{id}")
    suspend fun getSingleEpisode(@Path("id") id: String): EpisodeDto

    @GET("location/{id}")
    suspend fun getSingleLocation(@Path("id") id: String): LocationDto

}