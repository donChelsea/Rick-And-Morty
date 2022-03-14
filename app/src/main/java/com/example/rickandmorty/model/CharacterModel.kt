package com.example.rickandmorty.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo3.ApolloClient
import com.example.rickandmorty.CharacterQuery
import com.example.rickandmorty.CharactersQuery
import com.example.rickandmorty.Contract
import com.example.rickandmorty.di.DepedencyInjection

class CharacterModel : Contract.Model {

    private val _characters = MutableLiveData<List<CharactersQuery.Result?>>()
    val characters: LiveData<List<CharactersQuery.Result?>> = _characters

    private val _character = MutableLiveData<CharacterQuery.Character?>()
    val character: LiveData<CharacterQuery.Character?> = _character

    private val apolloClient = DepedencyInjection.apolloClient

    override suspend fun onCharactersRequested() {
        val response = apolloClient.query(CharactersQuery()).execute()
        response.data?.characters?.results?.let {
            _characters.value = it
        }
    }

    override suspend fun onCharacterRequested(id: String) {
        val response = apolloClient.query(CharacterQuery(id)).execute()
        response.data?.character.let {
            _character.value = it
        }
    }

}