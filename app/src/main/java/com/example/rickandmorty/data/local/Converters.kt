package com.example.rickandmorty.data.local

import androidx.room.TypeConverter
import com.example.rickandmorty.domain.models.Location
import com.example.rickandmorty.domain.models.Origin
import com.google.gson.Gson

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun originToString(origin: Origin): String = gson.toJson(origin)

    @TypeConverter
    fun stringToOrigin(value: String): Origin {
        return gson.fromJson(value, Origin::class.java)
    }

    @TypeConverter
    fun locationToString(location: Location): String = gson.toJson(location)

    @TypeConverter
    fun stringToLocation(value: String): Location {
        return gson.fromJson(value, Location::class.java)
    }
}