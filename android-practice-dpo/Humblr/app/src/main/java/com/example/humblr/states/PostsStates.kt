package com.example.humblr.states
//Вид постов
sealed class PostsStates {
    object New:PostsStates()
    object Popular:PostsStates()
    object Search:PostsStates()
}