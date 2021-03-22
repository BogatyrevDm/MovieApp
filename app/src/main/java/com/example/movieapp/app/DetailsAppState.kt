package com.example.movieapp.app

import com.example.movieapp.model.Film

sealed class DetailsAppState {
    data class Success(val movieData: Film) : DetailsAppState()
    data class Error(val error: Throwable) : DetailsAppState()
    object Loading : DetailsAppState()
}