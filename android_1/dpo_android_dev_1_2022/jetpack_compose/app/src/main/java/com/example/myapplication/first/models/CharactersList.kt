package com.example.myapplication.first.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
//Main Class in the model
@JsonClass(generateAdapter = true)
data class CharactersList(
    @Json(name = "info")
    val info: Info,
    @Json(name = "results")
    val results: List<Result>
)