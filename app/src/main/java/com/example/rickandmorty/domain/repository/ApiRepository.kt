package com.example.rickandmorty.domain.repository

import com.example.rickandmorty.util.Resource
import com.example.rickandmorty.domain.models.Character
import kotlinx.coroutines.flow.Flow

interface ApiRepository {

    suspend fun getAllCharacters(): Flow<Resource<List<Character>>>
}