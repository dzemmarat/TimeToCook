package ru.meowtee.timetocook.ui.custom

interface TypeWriterListener {
    fun onTypingStart(text: String?) {}
    fun onTypingEnd(text: String?) {}
    fun onCharacterTyped(text: String?, position: Int) {}
    fun onTypingRemoved(text: String?) {}
}