package com.example.rickandmorty.data.remote

import com.example.rickandmorty.util.Resource
import com.example.rickandmorty.domain.models.Character
import com.example.rickandmorty.domain.repository.ApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Singleton

@Singleton
class ApiRepositoryImpl(
    private val api: ApiService
) : ApiRepository {

    override suspend fun getAllCharacters(): Flow<Resource<List<Character>>> = flow {
        emit(Resource.Loading(isLoading = true))

        val characters = api.getAllCharacters().results
        with(characters) {
            emit(Resource.Success(map { it.toDomain() }))
        }
    }.catch { e ->
        emit(Resource.Error(message = e.message.toString()))
    }.flowOn(Dispatchers.IO)
}