package ru.meowtee.timetocook.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "ingredient")
data class Ingredient(

    @ColumnInfo(name = "name")
    val name: String = "",

    @ColumnInfo(name = "count")
    var count: Double = 0.0,
) : Parcelable