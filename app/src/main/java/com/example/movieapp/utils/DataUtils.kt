package com.example.movieapp.utils

import com.example.movieapp.model.Film
import com.example.movieapp.model.FilmDTO
import com.example.movieapp.model.FilmSummary
import com.example.movieapp.model.FilmSummaryDTO
import com.example.movieapp.room.HistoryEntity
import com.example.movieapp.room.NoteEntity

fun convertDTOToModel(filmDTO: FilmDTO): Film {
    return Film(
        FilmSummary(
            filmDTO.id!!, filmDTO.title!!,
            filmDTO.release_date!!, filmDTO.vote_average!!, filmDTO.poster_path, filmDTO.adult
        ), filmDTO.original_title!!,
        filmDTO?.overview!!, filmDTO.runtime!!, filmDTO.genres!!.joinToString(),
        filmDTO.vote_count!!, filmDTO.budget!!, filmDTO.revenue!!
    )
}

fun convertListDTOToModel(filmsDTO: List<FilmSummaryDTO>, showAdultContent: Boolean): List<Film> {
    val filmList: MutableList<Film> = mutableListOf()

    filmsDTO.forEach {
        if (showAdultContent || !it.adult) {
            filmList.add(
                Film(
                    FilmSummary(
                        it.id!!, it.title!!,
                        it.release_date!!, it.vote_average!!, it.poster_path
                    )
                )
            )
        }
    }
    return filmList.toList()
}

fun convertHistoryEntiryToFilm(entityList: List<HistoryEntity>): List<FilmSummary> {
    return entityList.map {
        FilmSummary(
            title = it.title,
            releaseDate = it.releaseDate,
            averageVote = it.averageVote.toDouble()
        )
    }

}

fun convertNoteEntiryToFilm(entity: NoteEntity?): FilmSummary {
    return entity?.let {
        FilmSummary(
            id = entity.id.toInt(),
            note = entity.note
        )
    } ?: FilmSummary()
}

fun convertFilmToHistoryEntity(film: FilmSummary): HistoryEntity {
    return HistoryEntity(
        title = film.title,
        releaseDate = film.releaseDate,
        averageVote = film.averageVote.toLong()
    )

}

fun convertFilmToNoteEntity(film: FilmSummary): NoteEntity {
    return NoteEntity(id = film.id.toLong(), note = film.note)

}
