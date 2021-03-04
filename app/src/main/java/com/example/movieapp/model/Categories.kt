package com.example.movieapp.model

enum class Categories {
    LATEST {
        override fun toString(): String {
            return "Latest"
        }
    },
    NOWPLAYING {
        override fun toString(): String {
            return "Now playing"
        }
    },
    POPULAR {
        override fun toString(): String {
            return "Popular"
        }
    },
    TOPRATED {
        override fun toString(): String {
            return "Top rated"
        }
    }
}