package com.blblblbl.myapplication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blblblbl.myapplication.domain.GetPhotosUseCase
import com.blblblbl.myapplication.domain.SavePhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    val savePhotoUseCase: SavePhotoUseCase,
):ViewModel() {
    fun savePhoto(uri:String,date:String){
        viewModelScope.launch {
            savePhotoUseCase.execute(uri,date)
        }
    }
}