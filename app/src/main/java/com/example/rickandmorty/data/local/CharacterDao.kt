package com.example.rickandmorty.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharacterDao {

    @Query("SELECT * FROM character_table")
    fun getCharacters(): List<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = CharacterEntity::class)
    suspend fun insert(character: CharacterEntity): Long
}