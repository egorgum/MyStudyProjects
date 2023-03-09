package com.example.humblr.viewModels

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.humblr.data.modelComments.Children
import com.example.humblr.repository.RetrofitRepository
import com.example.humblr.sharedPrefs.SharedPrefToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class OneSubViewModel @Inject constructor(private val repo: RetrofitRepository): ViewModel() {

    //Список комментариев
    private var _comments: MutableStateFlow<List<Children>> = MutableStateFlow(emptyList())
    val comments = _comments.asStateFlow()

    //Состояние загрузки
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

    //Получение комментариев
   suspend fun getComments(post: String, context: Context){
            try {
                val com =
                    repo.getComments(post = post, token = SharedPrefToken(context).getText()!!)
                if (com.isNotEmpty()) {
                    _comments.value = com[1].data.children
                }
                val a = _comments.value.toMutableList()
                if (a.isNotEmpty()) {
                    if (a[a.lastIndex].data.created_utc == null) {
                        a.removeAt(a.lastIndex)
                    }
                    _comments.value = a
                }
            }
            catch (e:Exception){
                Log.d(TAG,"error: $e")
            }
    }

    //Сохранить
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

    //Проголосовать
    fun changeVote(context: Context, name: String, dir: Int){
        viewModelScope.launch {
            repo.voteCom(dir = dir, id = name, token = SharedPrefToken(context).getText()!!)
        }
    }
}