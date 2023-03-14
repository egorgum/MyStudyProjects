package com.blblblbl.myapplication.domain

import com.blblblbl.myapplication.data.Photo
import com.blblblbl.myapplication.data.PhotosRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPhotosUseCase@Inject constructor(
    val photosRepository: PhotosRepository
) {
    fun execute(): Flow<List<Photo>> {
        return photosRepository.getAllPhotos()
    }
}