package com.sielehub.treasuremart.data.remote.dto

import com.sielehub.treasuremart.domain.model.Address

data class AddressDto(
    val city: String,
    val geolocation: GeolocationDto,
    val number: Int,
    val street: String,
    val zipcode: String
){
    fun toAddress():Address{
        return Address(
            city = city,
            geolocation = geolocation.toGeolocation(),
            number = number,
            street = street,
            zipcode = zipcode
        )
    }
}