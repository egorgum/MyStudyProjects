package com.example.recycler.api

import com.example.recycler.models.PhotosList
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
const val API_KEY = "o1ztbGeZTjRjyD4OSl5We5xVA70ZfJC9WXkeicff"
interface NasaApi {
    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    suspend fun getPhotos(@Query("sol")sol: Int,@Query("page")page: Int,@Query("api_key")api_key: String = API_KEY): PhotosList
}
object RetrofitService {
    private const val URL = "https://api.nasa.gov"
    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    val searchNasaAPI: NasaApi = retrofit.create(NasaApi::class.java)
}