package com.example.humblr.data.modelFriends

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "children")
    val children: List<Children>
)