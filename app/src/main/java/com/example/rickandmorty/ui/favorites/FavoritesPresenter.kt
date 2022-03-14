package com.example.rickandmorty.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rickandmorty.CharacterQuery
import com.example.rickandmorty.CharactersQuery
import com.example.rickandmorty.Contract
import com.example.rickandmorty.network.apolloClient

class FavoritesPresenter(
    private var view: Contract.View?,
) : Contract.Presenter {

    override suspend fun getData() {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        view = null
    }

}