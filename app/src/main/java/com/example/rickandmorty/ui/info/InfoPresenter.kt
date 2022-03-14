package com.example.rickandmorty.ui.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rickandmorty.CharacterQuery
import com.example.rickandmorty.Contract
import com.example.rickandmorty.network.apolloClient

class InfoPresenter(
    private var view: Contract.View?,
) : Contract.Presenter, Contract.Presenter.OnCharacterRequested {

    private val _character = MutableLiveData<CharacterQuery.Character?>()
    val character: LiveData<CharacterQuery.Character?> = _character

    override suspend fun fetchCharacter(id: String) {
        if (view != null) {
            view!!.showProgress()
        }

        val response = apolloClient.query(CharacterQuery(id)).execute()
        _character.value = response.data?.character
    }


    override fun onDestroy() {
        view = null
    }

}