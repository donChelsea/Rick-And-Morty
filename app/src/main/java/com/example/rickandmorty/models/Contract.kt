package com.example.rickandmorty.models

import com.example.rickandmorty.CharacterQuery
import com.example.rickandmorty.CharactersQuery

interface Contract {

    interface View {
        fun showProgress()
        fun hideProgress()
        fun setData(
            charactersUpdate: List<CharactersQuery.Result?>? = null,
            characterUpdate: CharacterQuery.Character? = null,
            savedData: List<Pair<String, Any?>>? = null
        )
        fun showError(error: String)
    }
}