package com.example.movieapp.repository

import com.example.movieapp.model.Categories
import com.example.movieapp.model.Film

interface Repository {
    fun getFilmsFromServer(): List<Film>
    fun getFilmsFromLocalStorage(): List<Film>
    fun getFilmCategories(): Map<Categories,List<Film>>
}