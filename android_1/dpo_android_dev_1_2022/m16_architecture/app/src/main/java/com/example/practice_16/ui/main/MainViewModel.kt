package com.example.practice_16.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practice_16.domain.GetUsefulActivityUseCase
import com.example.practice_16.entity.UsefulActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainViewModel @Inject constructor(private val useCase: GetUsefulActivityUseCase) : ViewModel() {
    private var _uaFlow: MutableStateFlow<UsefulActivity?> = MutableStateFlow(null)
    val uaFlow:StateFlow<UsefulActivity?> = _uaFlow.asStateFlow()

    fun reloadUsefulActivity(){
        viewModelScope.launch{
            _uaFlow.value = useCase.execute()
        }
    }
}