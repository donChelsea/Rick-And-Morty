package com.example.rickandmorty.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rickandmorty.CharacterQuery
import com.example.rickandmorty.Contract
import com.example.rickandmorty.network.apolloClient

class FavoritesPresenter(
    private var view: Contract.View?,
) : Contract.Presenter, Contract.Presenter.OnFavoritesRequested {

    private val _character = MutableLiveData<CharacterQuery.Character?>()
    val character: LiveData<CharacterQuery.Character?> = _character


    override fun onDestroy() {
        view = null
    }

    override suspend fun fetchFavorites() {
        if (view != null) {
            view!!.showProgress()
        }
    }

}