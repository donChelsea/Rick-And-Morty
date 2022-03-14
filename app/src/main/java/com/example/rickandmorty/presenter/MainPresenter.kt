package com.example.rickandmorty.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo3.api.ApolloResponse
import com.example.rickandmorty.CharactersQuery
import com.example.rickandmorty.Contract
import com.example.rickandmorty.network.apolloClient

class MainPresenter (
    private var mainView: Contract.View?,
    private val model: Contract.Model
) : Contract.Presenter,
    Contract.Presenter.OnNetworkCall {

    private val _characters = MutableLiveData<ApolloResponse<CharactersQuery.Data>>()
    val characters: LiveData<ApolloResponse<CharactersQuery.Data>> = _characters

    override suspend fun fetchCharacters() {
        _characters.value = apolloClient.query(CharactersQuery()).execute()

        mainView?.showProgress()
    }

    override fun onDestroy() {
        mainView = null
    }


}