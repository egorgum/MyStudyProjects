package com.example.humblr.data.modelFriends

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Children(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
)