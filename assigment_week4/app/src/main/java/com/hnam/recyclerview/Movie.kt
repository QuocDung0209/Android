package com.hnam.recyclerview

import java.util.*

data class Movie(val original_title: String, val overview: String, val poster_path: String,
                 val backdrop_path: String, val release_date: Date, val vote_average: Float, val video: Boolean)