package com.example.rest.Data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Dob(
    @Json(name = "age")val age: Int,
    @Json(name = "date")val date: String
)