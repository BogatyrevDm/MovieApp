package com.example.movieapp.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteEntity(
    @PrimaryKey
    val id: Long = 0,
    val note: String = ""
)