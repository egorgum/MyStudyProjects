package com.example.rest.ui.main
import com.example.rest.Data.User
import com.example.rest.ui.main.RetrofitService.searchUserAPI



class Repo{
    suspend fun getData(): User {
            return searchUserAPI.getUser()
    }
}


