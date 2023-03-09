package com.example.humblr.viewModels

import androidx.lifecycle.ViewModel
import com.example.humblr.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
@HiltViewModel
class OnboardViewModel @Inject constructor(): ViewModel() {
    //Заголовок
    var title: MutableStateFlow<Int> = MutableStateFlow(R.string.first_recommendation)
}