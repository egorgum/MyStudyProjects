package com.example.humblr.repository


import android.content.ContentValues
import android.util.Base64
import android.util.Log
import com.example.humblr.api.ResponseToken
import com.example.humblr.api.RetrofitService
import com.example.humblr.api.RetrofitServiceAuth
import com.example.humblr.api.ScalarsService
import com.example.humblr.data.modelComments.PupaItem
import com.example.humblr.data.modelFriends.FriendsInfo
import com.example.humblr.data.modelMe.MeItem
import com.example.humblr.data.modelSubList.SubredditData
import com.example.humblr.data.modelUser.UserInfo
import com.example.humblr.states.AuthConfiguration
import javax.inject.Inject

class RetrofitRepository @Inject constructor() {

    //Получение токена
    suspend fun getTokenRepo (code:String): ResponseToken {
        val encodedAuthString: String = Base64.encodeToString("${AuthConfiguration.CLIENT_ID}:${AuthConfiguration.SECRET}".toByteArray(), Base64.NO_WRAP)
        return RetrofitServiceAuth.searchAuth.getTokenRest(code = code, authHeader = "Basic $encodedAuthString")
    }

    //Получение новых постов
    suspend fun getSubsNew(token: String, page: String): SubredditData {
        val a = RetrofitService.searchInfo.getNewSubreddits(page = page, authHeader = "Bearer $token")
        Log.d(ContentValues.TAG, "список новых: $a")
        return a
    }

    //Получение популярных постов
    suspend fun getSubsPopular(token: String, page: String): SubredditData {
        val a = RetrofitService.searchInfo.getPopularSubreddits(page = page,
            authHeader = "Bearer $token"
        )
        Log.d(ContentValues.TAG, "список популярных: $a")
        return a
    }

    //Поиск постов
    suspend fun getSubsSearch(page: String, q: String, token: String): SubredditData {
        val a = RetrofitService.searchInfo.searchSubreddits(page, q ,
            authHeader = "Bearer $token"
        )
        Log.d(ContentValues.TAG, "список поиска: $a")
        return a
    }

    //Получение комментариев поста
    suspend fun getComments(post: String,  token: String): List<PupaItem> {
        val a = RetrofitService.searchInfo.searchComments(post = post,
            authHeader = "Bearer $token"
        )
        Log.d(ContentValues.TAG, "список комментов: $a")
        return a
    }


    //Сохранить
    suspend fun save(id: String, token: String){
        RetrofitService.searchInfo.saveComment(
            id = id,
            authHeader = "Bearer $token"
        )
    }

    //Удалить из сохраненных
    suspend fun unSaveCom(id: String, token: String){
        RetrofitService.searchInfo.unSaveComment(
            id = id,
            authHeader = "Bearer $token"
        )
    }

    //Проголосовать
    suspend fun voteCom(id: String, token: String, dir: Int){
        RetrofitService.searchInfo.vote(
            id = id,
            authHeader = "Bearer $token",
            dir = dir
        )
    }

    //Получить информацию о пользователе
    suspend fun getUser(name: String,  token: String): UserInfo {
        val a = RetrofitService.searchInfo.getUserRest(username = name,
            authHeader = "Bearer $token"
        )
        Log.d(ContentValues.TAG, "user: $a")
        return a
    }

    //Получить пользовательские комментарии
    suspend fun getUserComments(name: String,  token: String): PupaItem {
        val a = RetrofitService.searchInfo.getUserComments(username = name,
            authHeader = "Bearer $token"
        )
        Log.d(ContentValues.TAG, "список комментов: $a")
        return a
    }

    //Добавить в друзья
    suspend fun addToFriends(name: String, token: String) {
            ScalarsService.searchScalars.addToFriends(
                username = name,
                authHeader = "Bearer $token",
                requestBody = "{\"name\": \"$name\"}")
    }

    //Удалить из друзей
    suspend fun deleteFriend(name: String, token: String) {
        RetrofitService.searchInfo.deleteToFriends(
            username = name,
            authHeader = "Bearer $token")
    }

    //Получить информацию обо мне
    suspend fun getMe(token: String): MeItem {
        val a = RetrofitService.searchInfo.getMe(
            authHeader = "Bearer $token"
        )
        Log.d(ContentValues.TAG, "me: $a")
        return a
    }

    //Получить моих друзей
    suspend fun getMyFiends(token: String): FriendsInfo {
        val a = RetrofitService.searchInfo.getMyFriends(
            authHeader = "Bearer $token"
        )
        Log.d(ContentValues.TAG, "friends: $a")
        return a
    }

    //Получить сохраненные посты
    suspend fun getSavedPosts(token: String, username: String):SubredditData{
        val a = RetrofitService.searchInfo.getSavedPosts(username = username,
            authHeader = "Bearer $token"
        )
        Log.d(ContentValues.TAG, "список сохраненных постов: $a")
        return a
    }

    //Получить сохраненные комментарии
    suspend fun getSavedComments(token: String, username: String):PupaItem{
        val a = RetrofitService.searchInfo.getSavedComments(username = username,
            authHeader = "Bearer $token"
        )
        Log.d(ContentValues.TAG, "список сохраненных комментов: $a")
        return a
    }




}