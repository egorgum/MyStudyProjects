package com.example.rest.Data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Result(
    @Json(name = "cell")val cell: String,
    @Json(name = "dob")val dob: Dob,
    @Json(name = "email")val email: String,
    @Json(name = "gender")val gender: String,
    @Json(name = "id")val id: Id,
    @Json(name = "location")val location: Location,
    @Json(name = "login")val login: Login,
    @Json(name = "name")val name: Name,
    @Json(name = "nat")val nat: String,
    @Json(name = "phone")val phone: String,
    @Json(name = "picture")val picture: Picture,
    @Json(name = "registered")val registered: Registered
)