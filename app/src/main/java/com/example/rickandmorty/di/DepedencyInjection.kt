package com.example.rickandmorty.di

import com.apollographql.apollo3.ApolloClient
import com.example.rickandmorty.model.CharacterModel

object DepedencyInjection {

    val apolloClient: ApolloClient =
        ApolloClient.Builder()
            .serverUrl("https://rickandmortyapi.com/graphql")
            .build()

    val characterModel: CharacterModel = CharacterModel()
}