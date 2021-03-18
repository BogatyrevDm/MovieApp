package com.example.movieapp.model
data class FilmsListDTO(
    val results: List<FilmSummaryDTO>,
)

data class FilmSummaryDTO(
    val adult: Boolean,
    val id: Int,
    val title: String,
    val release_date: String,
    val vote_average: Double,
    val poster_path:String
)