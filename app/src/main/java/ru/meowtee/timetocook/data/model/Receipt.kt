package ru.meowtee.timetocook.data.model

data class Receipt(
    val image: Int,
    val name: String,
    var isFavourite: Boolean,
    val tags: List<String>
)
