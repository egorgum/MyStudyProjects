package com.example.humblr.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.humblr.data.modelComments.Children
import com.example.humblr.data.modelComments.PupaItem
import com.example.humblr.data.modelUser.UserInfo
import com.example.humblr.repository.RetrofitRepository
import com.example.humblr.sharedPrefs.SharedPrefToken
import com.example.humblr.states.FriendState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repo: RetrofitRepository) : ViewModel() {
    private var _userInfo: MutableStateFlow<UserInfo?> = MutableStateFlow(null)
    val userInfo = _userInfo.asStateFlow()
    private var _comments: MutableStateFlow<List<Children>> = MutableStateFlow(emptyList())
    val comments = _comments.asStateFlow()
    //Состояние дружбы
    private var _friendState: MutableStateFlow<FriendState> = MutableStateFlow(FriendState.NotFriend)
    val friendState = _friendState.asStateFlow()
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

    //Получить информацию о пользователе
    suspend fun getUser(name:String, context: Context ){
        _userInfo.value = repo.getUser(name = name, token = SharedPrefToken(context).getText()!!)
    }

    //Получить комментарии пользователя
    suspend fun getComments(name: String, context: Context) {
        _comments.value = repo.getUserComments(
            name = name,
            token = SharedPrefToken(context).getText()!!
        ).data.children
        val a = _comments.value.toMutableList()
        if (a.isNotEmpty()) {
            if (a[a.lastIndex].data.created_utc == null) {
                a.removeAt(a.lastIndex)
            }
            _comments.value = a
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
    //Добавить в друзья
    suspend fun addToFriend(context: Context, name: String){
        repo.addToFriends(name = name, token = SharedPrefToken(context).getText()!!)
        _friendState.value = FriendState.Friend

    }
    //Удалить из друзей
    suspend fun deleteFriend(context: Context, name: String){
        repo.deleteFriend(name = name, token = SharedPrefToken(context).getText()!!)
        _friendState.value = FriendState.NotFriend
    }
    //Начальное состояние дружбы
    fun startedFriendState(){
        if (userInfo.value!!.data.is_friend){
            _friendState.value = FriendState.Friend
        }
    }


}