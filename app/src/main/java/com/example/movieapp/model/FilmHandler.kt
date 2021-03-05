package com.example.movieapp.model

fun GetCategoriesList():Map<Categories, List<Film>>{
    Categories.LATEST
    var list = listOf(Film(), Film(), Film())
    var map = mapOf(Categories.LATEST to list, Categories.NOWPLAYING to list, Categories.POPULAR to list, Categories.TOPRATED to list)
    return map
}