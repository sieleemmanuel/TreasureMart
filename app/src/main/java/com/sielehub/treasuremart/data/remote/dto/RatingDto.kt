package com.sielehub.treasuremart.data.remote.dto

import com.sielehub.treasuremart.domain.model.Rating

data class RatingDto(
    val count: Int,
    val rate: Double
){
    fun toRating() = Rating(count = count, rate = rate)
}