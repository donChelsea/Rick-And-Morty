package com.example.rickandmorty

import android.content.Context
import android.util.Log
import com.apollographql.apollo3.ApolloClient

interface Contract {

    interface View {
        fun showProgress()
        fun hideProgress()
        fun setData(
            charactersUpdate: List<CharactersQuery.Result?>? = null,
            characterUpdate: CharacterQuery.Character? = null,
            savedData: MutableMap<String, *>? = null
        )
        fun showError(error: String)
    }
}

class CharacterModel {

    interface OnFinishedListener {
        suspend fun onResultSuccess(
            charactersUpdate: List<CharactersQuery.Result?>? = null,
            characterUpdate: CharacterQuery.Character? = null
        )

        suspend fun onResultFailure(error: String)
    }

    suspend fun requestCharacters(onFinishedListener: OnFinishedListener) {
        val response = apolloClient.query(CharactersQuery()).execute()
        if (response.data != null) {
            response.data?.characters?.results?.let {
                onFinishedListener.onResultSuccess(charactersUpdate = it, characterUpdate = null)
            }
        } else {
            onFinishedListener.onResultFailure("Could not retrieve all characters.")
        }

    }

    suspend fun requestCharacter(id: String, onFinishedListener: OnFinishedListener) {
        val response = apolloClient.query(CharacterQuery(id)).execute()
        if (response.data != null) {
            response.data?.character.let {
                onFinishedListener.onResultSuccess(characterUpdate = it, charactersUpdate = null)
            }
        } else {
            onFinishedListener.onResultFailure("Could not retrieve character: $id.")
        }
    }

    companion object {
        private val apolloClient = ApolloClient.Builder()
            .serverUrl("https://rickandmortyapi.com/graphql")
            .build()
    }
}

class SharedPreferencesModel(context: Context) {

    interface OnFinishedListener {
        suspend fun onResultSuccess(data: MutableMap<String, *>)

        suspend fun onResultFailure(error: String)
    }

    private var pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    private var editor = pref.edit()

    suspend fun getCharacters(onFinishedListener: OnFinishedListener) {
        val response = pref.all
        if (response != null) {
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