package com.example.rickandmorty.data.remote

import com.example.rickandmorty.data.remote.dtos.CharacterDto
import com.example.rickandmorty.data.remote.dtos.ListResultDto
import retrofit2.http.GET

interface ApiService {

    @GET("character")
    suspend fun getAllCharacters(): ListResultDto<CharacterDto>

}