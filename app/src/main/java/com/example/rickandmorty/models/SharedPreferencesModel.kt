package com.example.rickandmorty.models

import android.content.Context

class SharedPreferencesModel(context: Context) {

    interface OnFinishedListener {
        suspend fun onResultSuccess(data: List<Pair<String, Any?>>)

        suspend fun onResultFailure(error: String)
    }

    private var pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    private var editor = pref.edit()

    suspend fun getCharacters(onFinishedListener: OnFinishedListener) {
        val response = pref.all.toList()
        if (response.isNotEmpty()) {
            onFinishedListener.onResultSuccess(response)
        } else {
            onFinishedListener.onResultFailure("No characters stored.")
        }
    }

    fun put(id: String, name: String) {
        editor.putString(id, name)
        editor.apply()
    }

    fun remove(id: String) {
        editor.remove(id)
        editor.apply()
    }

    companion object {
        private const val PREF_NAME: String = "RickAndMortyApp"
        private var PRIVATE_MODE: Int = 0
    }

}