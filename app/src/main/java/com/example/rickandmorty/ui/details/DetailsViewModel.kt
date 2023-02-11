package com.example.rickandmorty.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.domain.models.Character
import com.example.rickandmorty.domain.models.Episode
import com.example.rickandmorty.domain.repository.ApiRepository
import com.example.rickandmorty.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: ApiRepository,
) : ViewModel() {

    private val _state: MutableStateFlow<DetailsUiState> = MutableStateFlow(DetailsUiState())
    val state: StateFlow<DetailsUiState> = _state

    fun postEvent(event: DetailsUiEvent) {
        when (event) {
            is DetailsUiEvent.GetAllEpisodeAppearances -> getAllEpisodes(event.episodesString)
            is DetailsUiEvent.GetSingleEpisodeAppearance -> getSingleEpisode(event.episode)
            is DetailsUiEvent.GetLocation -> getLocation(event.locationId)
        }
    }

    private fun getAllEpisodes(episodesString: String) {
        viewModelScope.launch {
            repository.getMultipleEpisodes(episodesString).collect { result ->
                when (result) {
                    is Resource.Loading -> println("Loading: " + result.isLoading)
                    is Resource.Success -> _state.update { it.copy(episodes = result.data.orEmpty()) }
                    is Resource.Error -> println("Error: " + result.message.toString())
                }
            }
        }
    }

    private fun getSingleEpisode(episode: String) {
        viewModelScope.launch {
            repository.getSingleEpisode(episode).collect { result ->
                when (result) {
                    is Resource.Loading -> _state.update { it.copy(isLoading = result.isLoading) }
                    is Resource.Success -> _state.update { it.copy(episodes = result.data.orEmpty()) }
                    is Resource.Error -> println("Error: " + result.message.toString())
                }
            }
        }
    }
    private fun getLocation(locationId: String) {
        viewModelScope.launch {
            repository.getSingleLocation(locationId).collect { result ->
                when (result) {
                    is Resource.Loading -> _state.update { it.copy(isLoading = result.isLoading) }
                    is Resource.Success -> {
                        getResidentsFromLocation(result.data?.residents.orEmpty())
                    }
                    is Resource.Error -> println("Error: " + result.message.toString())
                }
            }
        }
    }

    private fun getResidentsFromLocation(charactersString: String) {
        viewModelScope.launch {
            repository.getMultipleCharacters(charactersString).collect { result ->
                when (result) {
                    is Resource.Loading -> _state.update { it.copy(isLoading = result.isLoading) }
                    is Resource.Success -> _state.update { it.copy(residents = result.data.orEmpty()) }
                    is Resource.Error -> println("Error: " + result.message.toString())
                }
            }
        }
    }

}

sealed class DetailsUiEvent {
    data class GetAllEpisodeAppearances(val episodesString: String) : DetailsUiEvent()
    data class GetSingleEpisodeAppearance(val episode: String) : DetailsUiEvent()
    data class GetLocation(val locationId: String) : DetailsUiEvent()
}

data class DetailsUiState(
    val character: Character? = null,
    val residents: List<Character> = emptyList(),
    val episodes: List<Episode> = emptyList(),
    val isLoading: Boolean = false,
)