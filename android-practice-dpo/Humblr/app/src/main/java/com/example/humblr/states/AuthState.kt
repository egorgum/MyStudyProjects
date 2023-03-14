package com.example.humblr.states
//Состояния авторизации
sealed class AuthState {
    object NotLoggedIn : AuthState()
    object LoggedIn : AuthState()
    class Error(val errorMsg: String) : AuthState()
}