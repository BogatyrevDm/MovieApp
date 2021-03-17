package com.example.movieapp.viewmodel

import com.example.movieapp.model.Film

sealed class AppState {
    data class Success(val movieData: List<Film>, val categoryPosition: Int) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}