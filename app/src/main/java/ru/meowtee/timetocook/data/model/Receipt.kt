package ru.meowtee.timetocook.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "receipt")
data class Receipt(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "image")
    val image: Int = 0,

    @ColumnInfo(name = "time")
    val time: String = "0 мин",

    @ColumnInfo(name = "rating")
    val rating: Int = 0,

    @ColumnInfo(name = "name")
    val title: String = "",

    @ColumnInfo(name = "isFavourite")
    var isFavourite: Boolean = false,

    @ColumnInfo(name = "ingredients")
    var ingredients: List<Ingredient> = emptyList(),

    @ColumnInfo(name = "portions")
    val portions: Int = 0,

    @ColumnInfo(name = "steps")
    val steps: MutableList<String> = mutableListOf()
) : Parcelable