package com.example.rickandmorty

import com.apollographql.apollo3.ApolloClient

interface Contract {

    interface View {
        fun showProgress()
        fun hideProgress()
        fun setData(
            charactersUpdate: List<CharactersQuery.Result?>? = null,
            characterUpdate: CharacterQuery.Character? = null
        )
        fun showError(error: String)
    }

    interface Model {
        interface OnFinishedLiOstener {
            fun onFinished(string: String?)
        }

        suspend fun getCharacters()

        suspend fun getCharacter(id: String)
    }

    interface Presenter {
        suspend fun getData()

        fun onDestroy()
    }
}

class CharacterModel {

    interface OnFinishedListener {
        suspend fun onResultSuccess(
            charactersUpdate: List<CharactersQuery.Result?>? = null,
            characterUpdate: CharacterQuery.Character? = null
        )

        suspend fun onResultFail(error: String)
    }

    suspend fun requestCharacters(onFinishedListener: OnFinishedListener) {
        val response = apolloClient.query(CharactersQuery()).execute()
        if (response.data != null) {
            response.data?.characters?.results?.let {
                onFinishedListener.onResultSuccess(charactersUpdate = it, characterUpdate = null)
            }
        } else {
            onFinishedListener.onResultFail("Could not retrieve all characters.")
        }

    }

    suspend fun requestCharacter(id: String, onFinishedListener: OnFinishedListener) {
        val response = apolloClient.query(CharacterQuery(id)).execute()
        if (response.data != null) {
            response.data?.character.let {
                onFinishedListener.onResultSuccess(characterUpdate = it, charactersUpdate = null)
            }
        } else {
            onFinishedListener.onResultFail("Could not retrieve character: $id.")
        }
    }

    companion object {
        private val apolloClient = ApolloClient.Builder()
            .serverUrl("https://rickandmortyapi.com/graphql")
            .build()
    }
}