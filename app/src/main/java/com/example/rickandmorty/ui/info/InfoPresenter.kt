package com.example.rickandmorty.ui.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rickandmorty.CharacterQuery
import com.example.rickandmorty.CharactersQuery
import com.example.rickandmorty.Contract
import com.example.rickandmorty.di.DepedencyInjection
import com.example.rickandmorty.network.apolloClient

class InfoPresenter(
    private var view: Contract.View?,
) : Contract.Presenter {

    private val _character = MutableLiveData<CharacterQuery.Character?>()
    val character: LiveData<CharacterQuery.Character?> = _character

    private val characterModel = DepedencyInjection.characterModel
    var characterId = ""

    override suspend fun getData() {
        if (view != null) {
            view?.showProgress()
        }

        if (characterId.isNotEmpty()) {
            characterModel.onCharacterRequested(characterId)
            _character.value = characterModel.character.value
        }

    }

    override fun onDestroy() {
        view = null
    }

}