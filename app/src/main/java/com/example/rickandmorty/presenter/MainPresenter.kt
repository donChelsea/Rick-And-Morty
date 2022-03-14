package com.example.rickandmorty.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rickandmorty.CharactersQuery
import com.example.rickandmorty.Contract
import com.example.rickandmorty.network.apolloClient

class MainPresenter (
    private var mainView: Contract.View?,
) : Contract.Presenter,
    Contract.Presenter.OnCharactersRequested {

    private val _characters = MutableLiveData<List<CharactersQuery.Result?>>()
    val characters: LiveData<List<CharactersQuery.Result?>> = _characters

    override suspend fun fetchCharacters() {
        if (mainView != null) {
            mainView!!.showProgress()
        }

        val response = apolloClient.query(CharactersQuery()).execute()
        _characters.value = response.data?.characters?.results
    }

    override fun onDestroy() {
        mainView = null
    }


}