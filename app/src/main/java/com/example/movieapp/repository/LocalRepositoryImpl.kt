package com.example.movieapp.repository

import com.example.movieapp.model.FilmSummary
import com.example.movieapp.room.DatabaseDao
import com.example.movieapp.utils.convertFilmToHistoryEntity
import com.example.movieapp.utils.convertFilmToNoteEntity
import com.example.movieapp.utils.convertHistoryEntiryToFilm
import com.example.movieapp.utils.convertNoteEntiryToFilm

class LocalRepositoryImpl(private val localDataSource: DatabaseDao) : LocalRepository {
    override fun getAllHistory(): List<FilmSummary> {
        return convertHistoryEntiryToFilm(localDataSource.allHistory())
    }

    override fun saveHistoryEntity(filmSummary: FilmSummary) {
        localDataSource.insertHistory(convertFilmToHistoryEntity(filmSummary))
    }

    override fun clearHistory(): List<FilmSummary> {
        localDataSource.clearHistory()
        return listOf()
    }

    override fun getNoteEntity(filmId: Long):FilmSummary? {
        return convertNoteEntiryToFilm(localDataSource.getNoteById(filmId))
    }

    override fun saveNoteEntity(filmSummary: FilmSummary) {
        localDataSource.insertNote(convertFilmToNoteEntity(filmSummary))
    }
}