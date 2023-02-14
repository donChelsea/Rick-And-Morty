package com.example.rickandmorty.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.domain.models.Character
import com.example.rickandmorty.domain.repository.DatabaseRepository
import com.example.rickandmorty.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository,
) : ViewModel() {

    private val _state: MutableStateFlow<List<Character>> = MutableStateFlow(emptyList())
    val state: StateFlow<List<Character>> = _state

    init {
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch {
            databaseRepository.getCharacters().collect { result ->
                when (result) {
                    is Resource.Loading -> println("Loading: ${result.isLoading}")
                    is Resource.Success -> {
                        result.data?.let { list ->
                            _state.update { list }
                        }
                    }
                    is Resource.Error -> println("Error: ${result.message}")
                }
            }
        }
    }
}