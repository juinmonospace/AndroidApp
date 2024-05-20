package com.example.myapplication.data

import com.example.myapplication.data.NoteEntity
import com.example.myapplication.Note

fun NoteEntity.asExternalModel(): Note = Note(
    id, title, content
)

fun Note.toEntity(): NoteEntity = NoteEntity(
    id, title, content
)