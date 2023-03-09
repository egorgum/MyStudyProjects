package com.example.myapplication.firstFragment.main.MapFragment.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Sight(
    @Json(name = "kinds")
    val kinds: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "point")
    val point: Point,
    @Json(name = "rate")
    val rate: Int,
    @Json(name = "wikidata")
    val wikidata: String?,
    @Json(name = "xid")
    val xid: String
)