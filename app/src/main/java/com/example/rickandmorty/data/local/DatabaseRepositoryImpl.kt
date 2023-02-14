package com.example.rickandmorty.data.local

import com.example.rickandmorty.data.mappers.toDomain
import com.example.rickandmorty.data.mappers.toEntity
import com.example.rickandmorty.domain.models.Character
import com.example.rickandmorty.domain.repository.DatabaseRepository
import com.example.rickandmorty.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Singleton

@Singleton
class DatabaseRepositoryImpl(
    private val dao: CharacterDao
) : DatabaseRepository {

    override suspend fun getCharacters(): Flow<Resource<List<Character>>> = flow {
        emit(Resource.Loading(isLoading = true))

        val characters = dao.getCharacters()
        with(characters) { emit(Resource.Success(data = map { it.toDomain() })) }
    }.catch { e ->
        emit(Resource.Error(message = e.message.toString()))
    }.flowOn(Dispatchers.IO)

    override suspend fun insert(character: Character) = dao.insert(character.toEntity())

}