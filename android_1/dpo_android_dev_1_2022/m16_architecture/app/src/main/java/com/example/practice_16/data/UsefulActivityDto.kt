package com.example.practice_16.data

import com.example.practice_16.entity.UsefulActivity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UsefulActivityDto(

    @Json(name = "activity")
    override val activity: String,

    @Json(name = "type")
    override val type: String,

    @Json(name = "participants")
    override val participants: Int,

    @Json(name = "price")
    override val price: Double,

    @Json(name = "link")
    override val link: String,

    @Json(name = "key")
    override val key: String,

    @Json(name = "accessibility")
    override val accessibility: Double

    ): UsefulActivity