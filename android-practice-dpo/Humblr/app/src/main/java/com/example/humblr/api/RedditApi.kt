package com.example.humblr.api

import com.example.humblr.data.modelComments.PupaItem
import com.example.humblr.data.modelFriends.FriendsInfo
import com.example.humblr.data.modelMe.MeItem
import com.example.humblr.data.modelSubList.SubredditData
import com.example.humblr.data.modelUser.UserInfo
import com.example.humblr.states.AuthConfiguration
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

interface RedditApi {
    //Получение токена
    @POST("/api/v1/access_token")
    suspend fun getTokenRest(
        @Header("Authorization") authHeader: String,
        @Query("grant_type") grantType: String = "authorization_code",
        @Query("code") code: String,
        @Query("redirect_uri") redirectUri: String = AuthConfiguration.REDIRECT_URL,
    ): ResponseToken

    //Получить новые посты
    @GET("/r/popular/new")
    suspend fun getNewSubreddits(
        @Header("Authorization") authHeader: String,
        @Query("after")page:String
    ): SubredditData

    //Получить популярные посты
    @GET("/r/popular")
    suspend fun getPopularSubreddits(
        @Header("Authorization") authHeader: String,
        @Query("after")page:String
    ): SubredditData

    //Поиск постов
    @GET("/search")
    suspend fun searchSubreddits(
        @Query("after") page:String,
        @Query("q") q:String,
        @Header("Authorization") authHeader:String
    ): SubredditData

    //Получение комментариев поста
    @GET("/comments/{post}")
    suspend fun searchComments(
        @Path("post") post: String,
        @Header("Authorization") authHeader:String
    ): List<PupaItem>

    //Сохранение поста или комментария
    @POST("/api/save")
    suspend fun saveComment(
        @Header("Authorization") authHeader:String,
        @Query("id")id:String
    )

    //Удаление из сохраненных
    @POST("/api/unsave")
    suspend fun unSaveComment(
        @Header("Authorization") authHeader:String,
        @Query("id")id:String
    )

    //Проголосовать за пост
    @POST("/api/vote")
    suspend fun vote(
        @Header("Authorization") authHeader:String,
        @Query("id")id:String,
        @Query("dir")dir:Int
    )

    //Получить информацию о пользователе
    @GET("/user/{username}/about")
    suspend fun getUserRest(
        @Path("username") username: String,
        @Header("Authorization") authHeader:String): UserInfo

    //Получить комментарии, исходящие от пользователя
    @GET("/user/{username}/comments")
    suspend fun getUserComments(
        @Path("username") username: String,
        @Header("Authorization") authHeader:String): PupaItem

    //Добавить друга
    @PUT("/api/v1/me/friends/{username}")
    suspend fun addToFriends(
        @Path("username") username:String,
        @Body requestBody:String,
        @Header("Authorization") authHeader:String)

    //Удалить друга
    @DELETE("/api/v1/me/friends/{username}")
    suspend fun deleteToFriends(
        @Path("username") username:String,
        @Header("Authorization") authHeader:String):Response<Unit>

    //Получить информацию обо мне
    @GET("/api/v1/me")
    suspend fun getMe(
        @Header("Authorization") authHeader:String): MeItem

    //Получить моих друзей
    @GET("/api/v1/me/friends")
    suspend fun getMyFriends(
        @Header("Authorization") authHeader:String): FriendsInfo

    //Мои сохраненные посты
    @GET("/user/{username}/saved")
    suspend fun getSavedPosts(
        @Path("username") username:String,
        @Header("Authorization") authHeader: String,
        @Query("type")type:String = "links"
    ): SubredditData

    //Мои сохраненные комментарии
    @GET("/user/{username}/saved")
    suspend fun getSavedComments(
        @Path("username") username:String,
        @Header("Authorization") authHeader: String,
        @Query("type")type:String = "comments"
    ): PupaItem




}
//Сервис авторизации
object RetrofitServiceAuth {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.reddit.com")
        .addConverterFactory(MoshiConverterFactory.create().asLenient())
        .build()
    val searchAuth: RedditApi = retrofit.create(RedditApi::class.java)
}

//Сервис для получения информации из апи (Moshi)
object RetrofitService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://oauth.reddit.com").client(OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            }
        ).build())
        .addConverterFactory(MoshiConverterFactory.create().asLenient())
        .build()
    val searchInfo: RedditApi = retrofit.create(RedditApi::class.java)
}

//Сервис для получения информации из апи (Scalars)
object ScalarsService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://oauth.reddit.com")
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
    val searchScalars: RedditApi = retrofit.create(RedditApi::class.java)
}

//Токен
@JsonClass(generateAdapter = true)
data class ResponseToken(
    @Json(name = "access_token")
    val access_token: String
)
