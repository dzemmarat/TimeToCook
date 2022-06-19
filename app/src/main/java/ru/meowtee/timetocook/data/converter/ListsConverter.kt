package ru.meowtee.timetocook.data.converter

import androidx.room.TypeConverter
import ru.meowtee.timetocook.data.model.Ingredient

class ListsConverter {

    @TypeConverter
    fun toListIngredient(data: List<Ingredient>): String {
        return data.joinToString(separator = "") { "${it.name}&${it.count}|" }
    }

    @TypeConverter
    fun toListIngredient(data: String): List<Ingredient> {
        return data.split(
            "|",
            limit = data.count { it == '|' }
        ).takeIf { it.isNotEmpty() }?.map {
            val splitString = it.split('&')
            Ingredient(
                name = splitString[0],
                count = splitString[1].replace("|", "").toDouble()
            )
        } ?: emptyList()
    }

    @TypeConverter
    fun fromListString(data: List<String?>): String {
        return data.joinToString("|")
    }

    @TypeConverter
    fun toListString(data: String): List<String> {
        return data.split("|")
    }
}