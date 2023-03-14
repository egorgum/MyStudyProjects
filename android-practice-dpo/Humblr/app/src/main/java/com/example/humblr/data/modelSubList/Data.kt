package com.example.humblr.data.modelSubList

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "after")
    val after: String?,
    @Json(name = "children")
    val children: List<Children>,
    @Json(name = "dist")
    val dist: Int?,
    @Json(name = "geo_filter")
    val geo_filter: String?,
    @Json(name = "modhash")
    val modhash: Any?
)