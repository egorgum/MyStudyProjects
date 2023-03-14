package com.example.recycler.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.recycler.models.Photo
import com.example.recycler.repo.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repo: Repository) : ViewModel() {
    private val _photos = MutableStateFlow<List<Photo>>(emptyList())
    val photos = _photos.asStateFlow()

    val pagingPhotos: Flow<PagingData<Photo>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = {MarsPagingSource()}
    ).flow.cachedIn(viewModelScope)
}