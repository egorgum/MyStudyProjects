package com.example.practice_16.data

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


interface UsefulActivityDataSource {
    @GET("activity/")
    suspend fun getUsefulActivity(): UsefulActivityDto
}
object RetrofitService {
    private const val URL = "https://www.boredapi.com/api/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    val searchUsefulActivityAPI: UsefulActivityDataSource = retrofit.create(UsefulActivityDataSource::class.java)
}