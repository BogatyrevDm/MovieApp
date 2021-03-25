package com.example.movieapp.app

import com.example.movieapp.model.FilmSummary

sealed class HistoryAppState {
    data class Success(
        var filmData: List<FilmSummary> = listOf()
                       ) : HistoryAppState()
    data class Error(val error: Throwable) : HistoryAppState()
    object Loading : HistoryAppState()
}