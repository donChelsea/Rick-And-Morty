package com.example.rickandmorty.ui.details

import androidx.lifecycle.ViewModel
import com.example.rickandmorty.domain.models.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor() : ViewModel() {

    private val _state: MutableStateFlow<Character?> = MutableStateFlow(null)
    val state: StateFlow<Character?> = _state

    fun postEvent(event: DetailsUiEvent) {
        when (event) {
            is DetailsUiEvent.UpdateCharacter -> _state.update { event.character }
        }
    }

}

sealed class DetailsUiEvent {
    data class UpdateCharacter(val character: Character) : DetailsUiEvent()
}
