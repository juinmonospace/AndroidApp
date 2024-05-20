package com.example.myapplication.ui.theme

sealed interface NoteEvent {
    data class TitleChange(val value: String): NoteEvent
    data class ContentChange(val value: String): NoteEvent
    object Save : NoteEvent
    object NavigateBack : NoteEvent
    object DeleteNote : NoteEvent
}