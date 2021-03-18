package com.example.movieapp.model

enum class Categories()  {

    NOWPLAYING(){
        override fun toString(): String {
            return "now_playing"
        }
                             },
    POPULAR(){
        override fun toString(): String {
            return "popular"
        }
    } ,
    TOPRATED (){
        override fun toString(): String {
            return "top_rated"
        }
    },
    UPCOMING (){
        override fun toString(): String {
            return "upcoming"
        }
    }

}