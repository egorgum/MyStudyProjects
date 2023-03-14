package com.example.humblr.data.modelSubList

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DataX(
    @Json(name = "author")
    var author: String?,
    @Json(name = "id")
    var id: String?,
    @Json(name = "num_comments")
    var num_comments: Int?,
    @Json(name = "selftext")
    var selftext: String?,
    @Json(name = "title")
    var title: String?,
    @Json(name = "url")
    var url: String,
    @Json(name = "permalink")
    var permalink: String?,
    @Json(name = "saved")
    var saved: Boolean,
    @Json(name = "name")
    var name: String,

)