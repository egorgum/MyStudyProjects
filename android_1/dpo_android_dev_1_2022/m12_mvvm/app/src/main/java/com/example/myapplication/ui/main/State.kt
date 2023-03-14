package com.example.myapplication.ui.main

sealed class State {
    object Loading : State()
    object Result: State()
    object Start: State()
    data class Error(val message: String) : State()
}