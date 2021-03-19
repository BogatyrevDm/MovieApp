package com.example.movieapp.repository

import com.example.movieapp.model.Categories
import com.example.movieapp.model.Film
import com.example.movieapp.model.FilmDTO
import com.example.movieapp.model.FilmsListDTO
import retrofit2.Callback

interface Repository {
    fun getFilmsFromServer(category: Categories, showAdultContent:Boolean,callback: Callback<FilmsListDTO>)
    fun getFilmsFromLocalStorage(): List<Film>
    fun getFilmCategories(): List<Film>
}