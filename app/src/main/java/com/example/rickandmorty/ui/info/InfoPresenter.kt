package com.example.rickandmorty.ui.info

import com.example.rickandmorty.CharacterModel
import com.example.rickandmorty.CharacterQuery
import com.example.rickandmorty.CharactersQuery
import com.example.rickandmorty.Contract

class InfoPresenter(
    private var view: Contract.View?,
    private val model: CharacterModel
) : CharacterModel.OnFinishedListener {

    var characterId: String = ""

    suspend fun getCharacter(id: String) {
        view?.showProgress()
        model.requestCharacter(id, this)
    }

    override suspend fun onResultSuccess(
        charactersUpdate: List<CharactersQuery.Result?>?,
        characterUpdate: CharacterQuery.Character?
    ) {
        if (characterUpdate != null) {
            view?.hideProgress()
            view?.setData(characterUpdate = characterUpdate)
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