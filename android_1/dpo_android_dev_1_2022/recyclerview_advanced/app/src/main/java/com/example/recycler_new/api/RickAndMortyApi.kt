package com.example.recycler_new.api

import com.example.recycler_new.models.CharactersList
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("character/")
    suspend fun getCharacters(@Query("page")page: Int):CharactersList
}
object RetrofitService{
    private const val URL = "https://rickandmortyapi.com/api/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    val searchRickAndMortyApi: RickAndMortyApi = retrofit.create(RickAndMortyApi::class.java)
}