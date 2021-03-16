package com.example.movieapp.repository

import com.example.movieapp.model.FilmDTO
import retrofit2.Callback

interface DetailsRepository {
    fun getFilmDetailsFromServer(filmId: String, callback: Callback<FilmDTO>)
}