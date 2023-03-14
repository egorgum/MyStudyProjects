package com.blblblbl.myapplication.domain

import com.blblblbl.myapplication.data.PhotosRepository
import javax.inject.Inject

class SavePhotoUseCase@Inject constructor(
    val photosRepository: PhotosRepository
) {
    suspend fun execute(uri:String,date:String){
        photosRepository.savePhoto(uri,date)
    }
}