package com.sielehub.treasuremart.data.remote.dto

import com.sielehub.treasuremart.domain.model.Geolocation

data class GeolocationDto(
    val lat: String,
    val long: String
) {
    fun toGeolocation(): Geolocation {
        return Geolocation(lat = lat, long = long)
    }
}