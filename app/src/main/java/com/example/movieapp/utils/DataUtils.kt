package com.example.movieapp.utils

import com.example.movieapp.model.Film
import com.example.movieapp.model.FilmDTO
import com.example.movieapp.model.FilmSummary
import com.example.movieapp.model.FilmSummaryDTO

fun convertDTOToModel(filmDTO: FilmDTO): Film {
    return Film(
        FilmSummary(
            filmDTO.id!!, filmDTO.title!!,
            filmDTO.release_date!!, filmDTO.vote_average!!,filmDTO.poster_path
        ), filmDTO.original_title!!,
        filmDTO?.overview!!, filmDTO.runtime!!, filmDTO.genres!!.joinToString(),
        filmDTO.vote_count!!, filmDTO.budget!!, filmDTO.revenue!!
    )
}

fun convertListDTOToModel(filmsDTO: List<FilmSummaryDTO>): List<Film> {
    val filmList:MutableList<Film> = mutableListOf()

    filmsDTO.forEach {
         filmList.add(Film(FilmSummary(it.id!!, it.title!!,
             it.release_date!!, it.vote_average!!,it.poster_path)))
    }
    return filmList.toList()
}
