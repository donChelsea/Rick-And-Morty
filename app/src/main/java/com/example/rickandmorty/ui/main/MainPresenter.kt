package com.example.rickandmorty.ui.main

import CharacterModel
import com.example.rickandmorty.CharacterQuery
import com.example.rickandmorty.CharactersQuery
import com.example.rickandmorty.models.Contract

class MainPresenter (
    private var view: Contract.View?,
    private val model: CharacterModel
) : CharacterModel.OnFinishedListener {

    suspend fun getCharacters() {
        view?.showProgress()
        model.requestCharacters(this)
    }

    override suspend fun onResultSuccess(
        charactersUpdate: List<CharactersQuery.Result?>?,
        characterUpdate: CharacterQuery.Character?
    ) {
        if (charactersUpdate?.isNotEmpty() == true) {
            view?.hideProgress()
            view?.setData(charactersUpdate = charactersUpdate)
        }
    }

    override suspend fun onResultFailure(error: String) {
        view?.hideProgress()
        view?.showError(error)
    }

    fun onDestroy() {
        view = null
    }
}