package com.example.rickandmorty.domain.repository

import com.example.rickandmorty.domain.models.Character
import com.example.rickandmorty.util.Resource
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {

    suspend fun getCharacters(): Flow<Resource<List<Character>>>

    suspend fun insert(character: Character): Long
}