package com.example.myapplication.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _state = MutableStateFlow<State>(State.Error("Not enough characters"))
    val state = _state.asStateFlow()
    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()

    fun onButtonClick(searchText: String) {
        viewModelScope.launch{
            _state.value = State.Loading
            delay(5_000)
            _state.value = State.Result
        }
    }

    fun onChangeText(searchText: String) {
        viewModelScope.launch{
            if(searchText.length < 3){
                _state.value = State.Error("Not enough characters")
                _error.send("Not enough characters")
            }
            else{
                if (state.value == State.Result){
                    _state.value = State.Result
                }
                else{
                    _state.value = State.Start
                }
            }
        }
    }


}
