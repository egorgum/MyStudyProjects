package com.example.myapplication.firstFragment.main.MapFragment.api




import com.example.myapplication.firstFragment.main.MapFragment.models.Sight
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "5ae2e3f221c38a28845f05b67665ac79ff1c42b10c47534704d4299c"
interface TripApi {
    @GET("/0.1/ru/places/bbox?")
    suspend fun getSights(@Query("lon_min")lon_min: Double = 37.6, @Query("lon_max")lon_max: Double = 37.7,
                          @Query("lat_min")lat_min: Double = 55.755, @Query("lat_max")lat_max: Double = 55.756,
                          @Query("format")format: String = "json", @Query("limit")limit: Int = 10,
                          @Query("apikey")apikey: String = API_KEY
    ): List<Sight>
}
object RetrofitService {
    private const val URL = "https://api.opentripmap.com"
    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    val searchTripAPI: TripApi = retrofit.create(TripApi::class.java)
}