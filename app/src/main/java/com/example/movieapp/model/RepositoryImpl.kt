package com.example.movieapp.model

class RepositoryImpl : Repository {
    override fun getFilmsFromServer(): List<Film> {
        return listOf(Film(), Film(), Film())
    }

    override fun getFilmsFromLocalStorage(): List<Film> {
        return listOf(Film(), Film(), Film())
    }


}