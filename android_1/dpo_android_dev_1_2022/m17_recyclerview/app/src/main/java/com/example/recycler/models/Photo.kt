package com.example.recycler.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Photo(
    @Json(name = "camera")
    val camera: Camera,
    @Json(name = "earth_date")
    val earth_date: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "img_src")
    val img_src: String,
    @Json(name = "rover")
    val rover: Rover,
    @Json(name = "sol")
    val sol: Int
)