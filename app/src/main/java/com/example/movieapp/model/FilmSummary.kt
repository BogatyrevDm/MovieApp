package com.example.movieapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilmSummary(
    val id:Int = 464052,
    val title: String = "Wonder Woman 1984",
    val releaseDate: String = "2020-12-16",
    val averageVote: Double = 6.9
): Parcelable