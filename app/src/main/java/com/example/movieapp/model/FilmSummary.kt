package com.example.movieapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilmSummary(
    val id:Int = 0,
    val title: String = "",
    val releaseDate: String = "",
    val averageVote: Double = 0.0,
    val posterPath:String = ""
): Parcelable