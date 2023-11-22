package com.sielehub.treasuremart.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PostRequest(
    val  category : String,
    val  content : String,
    val  image : String,
    val  publishedAt : String,
    val  slug : String,
    val  status : String,
    val  thumbnail : String,
    val  title : String,
    val  updatedAt : String,
    val  url : String,
    val  userId : Int
)