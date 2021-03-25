package com.example.movieapp.repository

import com.example.movieapp.model.FilmSummary

interface LocalRepository {
    fun getAllHistory(): List<FilmSummary>
    fun saveHistoryEntity(filmSummary: FilmSummary)
    fun clearHistory():List<FilmSummary>
    fun getNoteEntity(filmId: Long):FilmSummary?
    fun saveNoteEntity(filmSummary: FilmSummary)
}