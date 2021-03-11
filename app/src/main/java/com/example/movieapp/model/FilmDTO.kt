package com.example.movieapp.model

data class FilmDTO (
    val title: String?,
    val original_title: String?,
    val runtime: Int?,
    val revenue: Int?,
    val budget: Int?,
    val overview: String?,
    val releaseDate: String?,
    val vote_average: Double?
)