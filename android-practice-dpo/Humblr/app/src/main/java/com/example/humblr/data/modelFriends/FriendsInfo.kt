package com.example.humblr.data.modelFriends

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FriendsInfo(
    @Json(name = "data")
    val data: Data)