package com.example.humblr.states
//Вид сохраненных
sealed class SavedStates{
    object Comments: SavedStates()
    object Posts: SavedStates()
    object NotInfo: SavedStates()
}
