package com.example.movieapp.utils

import com.example.movieapp.model.Film
import com.example.movieapp.model.FilmDTO
import com.example.movieapp.model.FilmSummary

fun convertDTOToModel(filmDTO: FilmDTO): Film {
    return Film(
        FilmSummary(
            filmDTO.id!!, filmDTO.title!!,
            filmDTO.release_date!!, filmDTO.vote_average!!
        ), filmDTO.original_title!!,
        filmDTO?.overview!!, filmDTO.runtime!!, filmDTO.genres!!.joinToString(),
        filmDTO.vote_count!!, filmDTO.budget!!, filmDTO.revenue!!, filmDTO.poster_path
    )
}
