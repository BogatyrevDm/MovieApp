package com.example.movieapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilmDTO (
    val id: Int?,
    val title: String?,
    val original_title: String?,
    val runtime: Int?,
    val revenue: Int?,
    val budget: Int?,
    val overview: String?,
    val release_date: String?,
    val vote_average: Double?,
    val vote_count: Int?,
    val genres: List<Genre>?
):Parcelable

@Parcelize
data class Genre(
    val id: Int,
    val name: String

):Parcelable{
    override fun toString(): String {
        return name
    }
}