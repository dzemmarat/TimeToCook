package ru.meowtee.timetocook.data.converter

import androidx.room.TypeConverter

class ListsConverter {
    @TypeConverter
    fun fromListString(data: List<String?>): String {
        return data.joinToString(",")
    }

    @TypeConverter
    fun toListString(data: String): List<String> {
        return data.split(",")
    }
}