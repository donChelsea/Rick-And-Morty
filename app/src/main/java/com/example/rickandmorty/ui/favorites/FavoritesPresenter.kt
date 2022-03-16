package com.example.rickandmorty.ui.favorites

import com.example.rickandmorty.models.Contract
import com.example.rickandmorty.models.SharedPreferencesModel

class FavoritesPresenter(
    private var view: Contract.View?,
    private val model: SharedPreferencesModel
) : SharedPreferencesModel.OnFinishedListener {

    suspend fun getData() {
        view?.showProgress()
        model.getCharacters(this)
    }

    suspend fun add(id: String, name: String) {
        model.put(id, name)
        getData()
    }

    suspend fun remove(id: String) {
        model.remove(id)
        getData()
    }

    override suspend fun onResultSuccess(data: List<Pair<String, Any?>>) {
        view?.setData(savedData = data)
        view?.hideProgress()
    }

    override suspend fun onResultFailure(error: String) {
        view?.showError(error)
        view?.hideProgress()
    }

    fun onDestroy() {
        view = null
    }
}