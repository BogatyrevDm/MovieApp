package com.example.movieapp.model

class RepositoryImpl : Repository {
    override fun getFilmsFromServer(): List<Film> = listOf(Film(), Film(), Film())


    override fun getFilmsFromLocalStorage(): List<Film> =  listOf(Film(), Film(), Film())


    override fun getFilmCategories(): Map<Categories, List<Film>> = GetCategoriesList()

}