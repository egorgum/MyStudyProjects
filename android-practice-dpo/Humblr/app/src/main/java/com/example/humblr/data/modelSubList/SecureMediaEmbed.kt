package com.example.humblr.data.modelSubList

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SecureMediaEmbed(
    @Json(name = "content")
    val content:String?,
    @Json(name = "width")
    val width:Int?,
    @Json(name = "scrolling")
    val scrolling:Boolean?,
    @Json(name = "media_domain_url")
    val media_domain_url:String?,
    @Json(name = "height")
    val height:Int?,
)