package com.example.movieapp.model

interface Repository {
    fun getFilmsFromServer(): List<Film>
    fun getFilmsFromLocalStorage(): List<Film>
}