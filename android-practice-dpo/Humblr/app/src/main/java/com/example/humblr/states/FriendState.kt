package com.example.humblr.states
//Состояние пользователя по отношению ко мне
sealed class FriendState{
    object Friend:FriendState()
    object NotFriend:FriendState()
}
