package com.example.movieapp.viewmodel

import com.example.movieapp.model.Film

sealed class AppState {
    data class Success(
        var NowPlayingData: List<Film> = listOf(),
        var PopularData: List<Film> = listOf(),
        var TopRatedData: List<Film> = listOf(),
        var UpComingData: List<Film> = listOf()
                       ) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}