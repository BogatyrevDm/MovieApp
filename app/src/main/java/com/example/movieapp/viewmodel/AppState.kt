package com.example.movieapp.viewmodel

import com.example.movieapp.model.Categories
import com.example.movieapp.model.Film

sealed class AppState {
    data class Success(val movieData: Map<Categories, List<Film>>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}