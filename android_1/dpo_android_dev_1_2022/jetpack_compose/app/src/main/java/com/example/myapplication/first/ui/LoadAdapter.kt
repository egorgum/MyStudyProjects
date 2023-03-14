package com.example.myapplication.first.ui

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.ViewModel
import com.example.myapplication.first.models.Origin
import com.example.myapplication.first.repository.Repository


class Characters(val image: String, val name: String, val status: String, val locationInfo: String, val species: String, val gender: String, val origin: String){
}
class ListViewModel(val repository: Repository): ViewModel(){
    suspend fun load(page: Int): List<Characters>{
        val item = repository.getData(page)
        return item.results.map {
                Characters(
                    image = it.image,
                    name = it.name,
                    status = it.status,
                    locationInfo = it.location.name,
                    species = it.species,
                    gender = it.gender,
                    origin = it.origin.name
                )
        }
    }
}