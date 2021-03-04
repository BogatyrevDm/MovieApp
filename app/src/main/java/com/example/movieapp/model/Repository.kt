package com.example.movieapp.model

interface Repository {
    fun getFilmsFromServer(): List<Film>
    fun getFilmsFromLocalStorage(): List<Film>
    fun getFilmCategories(): Map<Categories,List<Film>>
}