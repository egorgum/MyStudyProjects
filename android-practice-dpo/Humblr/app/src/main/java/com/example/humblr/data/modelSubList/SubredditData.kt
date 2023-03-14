package com.example.humblr.data.modelSubList

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SubredditData(
    @Json(name = "data")
    val data: Data,
    @Json(name = "kind")
    val kind: String
)