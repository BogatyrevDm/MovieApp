package com.example.movieapp.repository

import com.example.movieapp.model.Categories
import com.example.movieapp.model.Film
import com.example.movieapp.model.FilmsListDTO
import retrofit2.Callback

class RepositoryImpl(private val remoteDataSource: RemoteDataSource) : Repository {
    override fun getFilmsFromServer(category: Categories, callback: Callback<FilmsListDTO>) {
        remoteDataSource.getFilmsDetails(category, callback)
    }


    override fun getFilmsFromLocalStorage(): List<Film> = listOf(Film(), Film(), Film())


    override fun getFilmCategories(): List<Film> = listOf()

}