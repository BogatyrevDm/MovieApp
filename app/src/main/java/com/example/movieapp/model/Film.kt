package com.example.movieapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film(
    val filmSummary: FilmSummary = FilmSummary(),
    val originalTitle: String = "Wonder Woman 1984",
    val overview: String = "Влиятельный бизнесмен Максвелл Лорд " +
            "ищет магические артефакты, которые помогли бы ему обрести силу и могущество " +
            "подобно богу. Однажды он встречает археолога Барбару Энн Минерву и просит у неё помощи в " +
            "достижении его цели. Их отношения портятся, когда один из артефактов превращает Минерву в Гепарду, вне себя " +
            "от ярости она желает ему отомстить за то, что с ней случилось. Лорд просит защиты у Дианы Принс, взамен он обещает, что воскресит " +
            "Стива Тревора с помощью одного из артефактов.",
    val runtime: Int = 151,
    val genres: String = "фэнтези, боевик, приключения",
    val voteCount: Int = 3961,
    val budget: Int = 200000000,
    val revenue: Int = 159533000
) : Parcelable

fun GetCategoriesList(): Map<Categories, List<Film>> {
    val list = listOf(Film(), Film(), Film())
    val map = mapOf(
        Categories.LATEST to list,
        Categories.NOWPLAYING to list,
        Categories.POPULAR to list,
        Categories.TOPRATED to list
    )
    return map
}