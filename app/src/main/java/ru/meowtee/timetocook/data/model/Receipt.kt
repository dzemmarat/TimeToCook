package ru.meowtee.timetocook.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "receipt")
data class Receipt(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "image")
    val image: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "isFavourite")
    var isFavourite: Boolean,

    @ColumnInfo(name = "tags")
    val tags: List<String>
)
