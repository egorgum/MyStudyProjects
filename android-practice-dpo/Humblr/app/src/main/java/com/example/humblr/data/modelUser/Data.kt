package com.example.humblr.data.modelUser

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "id")
    val id: String,
    @Json(name = "is_friend")
    val is_friend: Boolean,
    @Json(name = "is_gold")
    val is_gold: Boolean,
    @Json(name = "name")
    val name: String,
    @Json(name = "snoovatar_img")
    val snoovatar_img: String,
    @Json(name = "total_karma")
    val total_karma: Int,
)