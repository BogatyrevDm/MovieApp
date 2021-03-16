package com.example.movieapp.repository

import com.example.movieapp.BuildConfig
import com.example.movieapp.model.FilmDTO
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class RemoteDataSource {
    private val filmAPI = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        .build().create(FilmAPI::class.java)

    fun getFilmDetails(filmId: String, callback: Callback<FilmDTO>) {
        filmAPI.getFilm(filmId, BuildConfig.MOVIEDB_API_KEY, Locale.getDefault().toLanguageTag()).enqueue(callback)
    }
}