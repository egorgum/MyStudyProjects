package com.example.rest.ui.main

import com.example.rest.Data.User
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface SearchUserAPI {
    @GET("api/")
    suspend fun getUser(): User
}

object RetrofitService {
    private const val URL = "https://randomuser.me/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    val searchUserAPI: SearchUserAPI = retrofit.create(SearchUserAPI::class.java)
}