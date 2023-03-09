package com.example.humblr.data.modelComments

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PupaItem(
    @Json(name = "data")
    val data: Data,
)