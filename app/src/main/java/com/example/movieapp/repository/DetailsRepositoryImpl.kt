package com.example.movieapp.repository

import com.example.movieapp.model.FilmDTO
import retrofit2.Callback

class DetailsRepositoryImpl(private val remoteDataSource: RemoteDataSource) : DetailsRepository {
    override fun getFilmDetailsFromServer(filmId: String, callback: Callback<FilmDTO>) {
        remoteDataSource.getFilmDetails(filmId, callback)
    }
}