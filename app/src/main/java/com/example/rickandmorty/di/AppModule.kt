package com.example.rickandmorty.di

import com.example.rickandmorty.data.remote.ApiRepositoryImpl
import com.example.rickandmorty.data.remote.ApiService
import com.example.rickandmorty.domain.repository.ApiRepository
import com.example.rickandmorty.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideEventsRepository(api: ApiService): ApiRepository = ApiRepositoryImpl(api)

}