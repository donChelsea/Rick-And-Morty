package com.example.rickandmorty.data.remote

import com.example.rickandmorty.data.remote.dtos.CharacterDto
import com.example.rickandmorty.data.mappers.toDomain
import com.example.rickandmorty.util.Resource
import com.example.rickandmorty.domain.models.Character
import com.example.rickandmorty.domain.models.Episode
import com.example.rickandmorty.domain.models.Location
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

    override suspend fun getMultipleCharacters(charactersString: String): Flow<Resource<List<Character>>>  = flow {
        emit(Resource.Loading(isLoading = true))

        val characters = api.getMultipleCharacters(charactersString)
        with(characters) {
            emit(Resource.Success(map { it.toDomain() }))
        }
    }.catch { e ->
        emit(Resource.Error(message = e.message.toString()))
    }.flowOn(Dispatchers.IO)

    override suspend fun getMultipleEpisodes(allEpisodesString: String): Flow<Resource<List<Episode>>> = flow {
        emit(Resource.Loading(isLoading = true))

        val episodes = api.getMultipleEpisodes(allEpisodesString)
        with(episodes) {
            emit(Resource.Success(data = map { it.toDomain() }))
        }
    }.catch { e ->
        emit(Resource.Error(message = e.message.toString()))
    }.flowOn(Dispatchers.IO)

    override suspend fun getSingleEpisode(episodeId: String): Flow<Resource<List<Episode>>> = flow {
        emit(Resource.Loading(isLoading = true))

        val episode = api.getSingleEpisode(episodeId)
        with(episode) {
            emit(Resource.Success(data = listOf(toDomain())))
        }
    }.catch { e ->
        emit(Resource.Error(message = e.message.toString()))
    }.flowOn(Dispatchers.IO)

    override suspend fun getSingleLocation(locationId: String): Flow<Resource<Location>> = flow {
        emit(Resource.Loading(isLoading = true))

        val location = api.getSingleLocation(locationId)
        with(location) {
            emit(Resource.Success(data = toDomain()))
        }
    }.catch { e ->
        emit(Resource.Error(message = e.message.toString()))
    }.flowOn(Dispatchers.IO)

}