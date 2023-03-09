package com.example.humblr.data.modelMe

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MeItem(
    @Json(name = "id")
    val id: String,
    @Json(name = "is_gold")
    val is_gold: Boolean,
    @Json(name = "name")
    val name: String,
    @Json(name = "num_friends")
    val num_friends: Int,
    @Json(name = "icon_img")
    val icon_img: String,
    @Json(name = "total_karma")
    val total_karma: Int,
)