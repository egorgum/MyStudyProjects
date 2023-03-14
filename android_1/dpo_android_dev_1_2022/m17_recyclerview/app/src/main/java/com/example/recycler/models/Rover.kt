package com.example.recycler.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Rover(
    @Json(name = "id")
    val id: Int,
    @Json(name = "landing_date")
    val landing_date: String,
    @Json(name = "launch_date")
    val launch_date: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "status")
    val status: String
)