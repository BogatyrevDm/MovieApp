package com.example.movieapp.app

import com.example.movieapp.model.FilmSummary

sealed class NoteAppState {
    data class Success(
        var filmData: FilmSummary? = FilmSummary()
                       ) : NoteAppState()
    data class Error(val error: Throwable) : NoteAppState()
    object Loading : NoteAppState()
}