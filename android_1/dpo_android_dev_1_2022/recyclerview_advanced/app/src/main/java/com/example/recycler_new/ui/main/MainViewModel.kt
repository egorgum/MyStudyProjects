package com.example.recycler_new.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.recycler_new.api.RickAndMortyApi
import com.example.recycler_new.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(private val repository: Repository) : ViewModel() {
    val pagingPersons: Flow<PagingData<com.example.recycler_new.models.Result>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = {RickAndMortyPagingSource()}
    ).flow.cachedIn(viewModelScope)
}