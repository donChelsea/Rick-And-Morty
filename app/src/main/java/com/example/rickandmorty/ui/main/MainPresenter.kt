package com.example.rickandmorty.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rickandmorty.CharactersQuery
import com.example.rickandmorty.Contract
import com.example.rickandmorty.di.DepedencyInjection

class MainPresenter (
    private var view: Contract.View?,
) : Contract.Presenter {

    private val _characters = MutableLiveData<List<CharactersQuery.Result?>>()
    val characters: LiveData<List<CharactersQuery.Result?>> = _characters

    private val characterModel = DepedencyInjection.characterModel

    override suspend fun getData() {
        if (view != null) {
            view?.showProgress()
        }

        characterModel.onCharactersRequested()
        _characters.value = characterModel.characters.value
    }

    override fun onDestroy() {
        view = null
    }


}