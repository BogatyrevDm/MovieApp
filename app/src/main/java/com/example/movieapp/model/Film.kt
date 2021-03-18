package com.example.movieapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film(
    val filmSummary: FilmSummary = FilmSummary(),
    val originalTitle: String = "",
    val overview: String = "",
    val runtime: Int = 0,
    val genres: String = "",
    val voteCount: Int = 0,
    val budget: Int = 0,
    val revenue: Int = 0
) : Parcelable

