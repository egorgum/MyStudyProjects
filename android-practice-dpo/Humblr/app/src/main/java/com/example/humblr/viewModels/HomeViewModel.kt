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
class HomeViewModel@Inject constructor(private val repo: RetrofitRepository) : ViewModel() {

    //Список постов
    var pagingSubs: Flow<PagingData<Children>>? = null

    //Состояние переключателя
    private var _switchState: MutableStateFlow<PostsStates> = MutableStateFlow(PostsStates.New)
    val switchState = _switchState.asStateFlow()

    //Статус зашрузки
    private var _isLoading:MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    //Получить прогресс
    fun loading(){
        _isLoading.value = true
    }

    //Убрать прогресс
    fun unloading(){
        _isLoading.value = false
    }

    //Сменить состояние переключателя
    fun changeSwitchState(vl: PostsStates){
        if (vl == PostsStates.Popular){
            _switchState.value = PostsStates.Popular
        }
        if (vl == PostsStates.New){
            _switchState.value = PostsStates.New
        }
    }

    //Сохранить
    fun changeOnSaved(context: Context,id: String){
        viewModelScope.launch {
            repo.save(id = id, token = SharedPrefToken(context).getText()!!)
        }
    }

    //Удалить из сохраненных
    fun changeOnUnSaved(context: Context,id: String){
        viewModelScope.launch {
            repo.unSaveCom(id = id, token = SharedPrefToken(context).getText()!!)
        }
    }


    //Пополнение списка постов
    fun paging(context: Context, state: PostsStates){
       pagingSubs = Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { SubredditsPagingSource(token = SharedPrefToken(context).getText().toString(), state = state) }
        ).flow.cachedIn(viewModelScope)
    }
}