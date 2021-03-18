package com.example.movieapp.repository

import com.example.movieapp.model.FilmDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmAPI {
    @GET("3/movie/{filmID}")
    fun getFilm(
        @Path("filmID") film: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<FilmDTO>
}