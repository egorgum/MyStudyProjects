package com.example.humblr.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.humblr.data.modelSubList.Children
import com.example.humblr.recycler.pagingSource.SubredditsPagingSource
import com.example.humblr.repository.RetrofitRepository
import com.example.humblr.sharedPrefs.SharedPrefToken
import com.example.humblr.states.PostsStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repo: RetrofitRepository) : ViewModel() {
    //Список найденых постов по запросу
    var pagingSubs: Flow<PagingData<Children>>? = null

    //Состояние прогресса
    private var _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    //Получить прогресс
    fun loading(){
        _isLoading.value = true
    }

    //Удалить прогресс
    fun unloading(){
        _isLoading.value = false
    }

    //Пополнение списка постов
    fun paging(context: Context, state: PostsStates, q: String){
        pagingSubs = Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { SubredditsPagingSource(token = SharedPrefToken(context).getText().toString(),
                state = state,
                param1 = q) }
        ).flow.cachedIn(viewModelScope)
    }

    //Сохранить+
    fun changeOnSaved(context: Context,id: String){
        viewModelScope.launch {
            repo.save(id = id, token = SharedPrefToken(context).getText()!!)
        }
    }
    //Удалить
    fun changeOnUnSaved(context: Context,id: String){
        viewModelScope.launch {
            repo.unSaveCom(id = id, token = SharedPrefToken(context).getText()!!)
        }
    }
}