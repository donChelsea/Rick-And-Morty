package com.example.rickandmorty.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rickandmorty.CharacterQuery
import com.example.rickandmorty.CharactersQuery
import com.example.rickandmorty.Contract
import com.example.rickandmorty.network.apolloClient

class InfoPresenter(
    private var mainView: Contract.View?,
) : Contract.Presenter, Contract.Presenter.OnCharacterRequested {

    private val _character = MutableLiveData<CharacterQuery.Character?>()
    val character: LiveData<CharacterQuery.Character?> = _character

    override suspend fun fetchCharacter(id: String) {
        if (mainView != null) {
            mainView!!.showProgress()
        }

        val response = apolloClient.query(CharacterQuery(id)).execute()
        _character.value = response.data?.character
    }


    override fun onDestroy() {
        mainView = null
    }

}