package com.example.humblr.data.modelComments

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DataX(
    @Json(name = "author")
    val author: String?,
    @Json(name = "body")
    val body: String?,
    @Json(name = "created_utc")
    val created_utc: Double?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "link_id")
    val link_id: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "saved")
    val saved: Boolean?,
    @Json(name = "selftext")
    val selftext: String?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "url")
    val url: String?,
    @Json(name = "author_fullname")
    val author_fullname: String?,
    @Json(name = "likes")
    val likes: Boolean?
)