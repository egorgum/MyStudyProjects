package com.example.rest.Data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Street(
    @Json(name = "name")val name: String,
    @Json(name = "number")val number: Int
)