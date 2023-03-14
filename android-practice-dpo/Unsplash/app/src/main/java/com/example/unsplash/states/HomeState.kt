package com.example.unsplash.states

import kotlinx.coroutines.flow.MutableStateFlow

sealed class HomeState {
    object SUCCESS : HomeState()
    class Error(val errorMsg: String) : HomeState()
    companion object{
        var state: MutableStateFlow<HomeState> = MutableStateFlow(SUCCESS)
    }
}
