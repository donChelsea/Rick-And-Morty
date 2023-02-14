package com.example.rickandmorty.di

import android.content.Context
import androidx.room.Room
import com.example.rickandmorty.data.local.CharacterDao
import com.example.rickandmorty.data.local.CharacterDatabase
import com.example.rickandmorty.data.local.DatabaseRepositoryImpl
import com.example.rickandmorty.domain.repository.DatabaseRepository
import com.example.rickandmorty.util.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCharacterDatabase(@ApplicationContext context: Context): CharacterDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            CharacterDatabase::class.java,
            DATABASE_NAME
        ).build()

    @Provides
    @Singleton
    fun provideCharacterDao(database: CharacterDatabase): CharacterDao =
        database.getCharacterDao()

    @Provides
    @Singleton
    fun provideDatabaseRepository(dao: CharacterDao): DatabaseRepository = DatabaseRepositoryImpl(dao)

}