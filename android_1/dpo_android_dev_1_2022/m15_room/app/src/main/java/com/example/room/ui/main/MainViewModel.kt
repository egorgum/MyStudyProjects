package com.example.room.ui.main

import android.app.Application
import android.database.SQLException
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private  val wordsDao: WordsDao) : ViewModel(){
    val allWords = this.wordsDao.getWords().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )



    fun addButton(text: String) {
        viewModelScope.launch {
            wordsDao.addWord(Words(word = text, number =  1))
        }
        allWords.value.forEach {
            if (text == it.word) {
                viewModelScope.launch {
                    wordsDao.updateNumber(it.copy(number = it.number + 1))
                }
            }
        }
    }

    fun onDelete(){
        viewModelScope.launch{
            wordsDao.deleteWords(allWords.value )
        }
    }
}