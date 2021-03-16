package com.example.movieapp.repository

import com.example.movieapp.model.Categories
import com.example.movieapp.model.Film
import com.example.movieapp.model.GetCategoriesList

class RepositoryImpl : Repository {
    override fun getFilmsFromServer(): List<Film> = listOf(Film(), Film(), Film())


    override fun getFilmsFromLocalStorage(): List<Film> =  listOf(Film(), Film(), Film())


    override fun getFilmCategories(): Map<Categories, List<Film>> = GetCategoriesList()

}