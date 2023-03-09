package com.example.rest.Data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Info(
    @Json(name = "page")val page: Int,
    @Json(name = "results")val results: Int,
    @Json(name = "seed")val seed: String,
    @Json(name = "version")val version: String
)