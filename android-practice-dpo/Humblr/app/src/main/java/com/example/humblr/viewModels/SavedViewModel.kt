package com.example.humblr.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.humblr.data.modelComments.Children
import com.example.humblr.data.modelComments.PupaItem
import com.example.humblr.repository.RetrofitRepository
import com.example.humblr.sharedPrefs.SharedPrefToken
import com.example.humblr.states.SavedStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(private val repo: RetrofitRepository) : ViewModel() {
    var username: String? = null

    //Состояние категории списка
    private var _state: MutableStateFlow<SavedStates> = MutableStateFlow(SavedStates.NotInfo)
    val state = _state.asStateFlow()

    private var _comments: MutableStateFlow<List<Children>?> = MutableStateFlow(emptyList())
    val comments = _comments.asStateFlow()

    private var _posts: MutableStateFlow<List<com.example.humblr.data.modelSubList.Children>?> = MutableStateFlow(
        emptyList()
    )
    val posts = _posts.asStateFlow()

    //прогресс
    private var _isLoading:MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()
    //Получить прогресс
    fun loading(){
        _isLoading.value = true
    }
    //Удалить прогресс
    fun unloading(){
        _isLoading.value = false
    }
    //Получить мой username для возможности получения сохраненных постов и комментариев
    suspend fun getUserName(context: Context){
        username = repo.getMe(SharedPrefToken(context).getText()!!).name
    }

    //Получить сохраненные комментарии
    suspend fun getSavedComments(context:Context, username:String){
        _comments.value =
        repo.getSavedComments(
            token = SharedPrefToken(context).getText()!!,
            username = username).data.children
        _state.value = SavedStates.Comments
    }

    //Получить сохраненные посты
    suspend fun getSavedPosts(context: Context, username: String){
            _posts.value = repo.getSavedPosts(
                token = SharedPrefToken(context).getText()!!,
                username = username
            ).data.children
            _state.value = SavedStates.Posts
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
    //Проголосовать
    fun changeVote(context: Context, name: String, dir: Int){
        viewModelScope.launch {
            repo.voteCom(dir = dir, id = name, token = SharedPrefToken(context).getText()!!)
        }
    }
}