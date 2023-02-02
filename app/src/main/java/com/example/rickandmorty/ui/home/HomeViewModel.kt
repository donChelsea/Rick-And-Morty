package com.example.rickandmorty.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.domain.models.Character
import com.example.rickandmorty.domain.repository.ApiRepository
import com.example.rickandmorty.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ApiRepository
) : ViewModel() {

    private val _state: MutableStateFlow<List<Character>> = MutableStateFlow(emptyList())
    val state: StateFlow<List<Character>> = _state

    init {
        viewModelScope.launch {
            repository.getAllCharacters().collect { result ->
                when (result) {
                    is Resource.Loading -> Log.d("HomeViewModel", "Loading: ${result.isLoading}")
                    is Resource.Success -> _state.update { result.data.orEmpty() }
                    is Resource.Error -> Log.d("HomeViewModel", "Error: ${result.message}")
                }
            }
        }
    }
}

//sealed class HomeUiState(
//    val characters: List<Character> = emptyList()
//)