package com.example.movieapp.repository

import com.example.movieapp.model.Categories
import com.example.movieapp.model.FilmsListDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Tag

interface FilmsAPI {
    @GET("3/movie/{categoryID}")
    fun getFilms(
        @Path("categoryID") film: String,
        @Tag category: Categories,
        @Tag showAdultContent: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<FilmsListDTO>
}