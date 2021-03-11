package com.example.movieapp.model

fun GetCategoriesList():Map<Categories, List<Film>>{
    val list = listOf(Film(), Film(), Film())
    val map = mapOf(Categories.LATEST to list, Categories.NOWPLAYING to list, Categories.POPULAR to list, Categories.TOPRATED to list)
    return map
}