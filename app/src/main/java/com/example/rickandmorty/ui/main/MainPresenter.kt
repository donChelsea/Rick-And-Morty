package com.example.rickandmorty.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rickandmorty.CharactersQuery
import com.example.rickandmorty.Contract
import com.example.rickandmorty.network.apolloClient

class MainPresenter (
    private var view: Contract.View?,
) : Contract.Presenter,
    Contract.Presenter.OnCharactersRequested {

    private val _characters = MutableLiveData<List<CharactersQuery.Result?>>()
    val characters: LiveData<List<CharactersQuery.Result?>> = _characters

    override suspend fun fetchCharacters() {
        if (view != null) {
            view!!.showProgress()
        }

        val response = apolloClient.query(CharactersQuery()).execute()
        response.data?.characters?.results.let {
            _characters.value = it
        }
    }

    override fun onDestroy() {
        view = null
    }


}