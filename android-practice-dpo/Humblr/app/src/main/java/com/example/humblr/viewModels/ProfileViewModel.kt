package com.example.humblr.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.humblr.data.modelFriends.Children
import com.example.humblr.data.modelMe.MeItem
import com.example.humblr.repository.RetrofitRepository
import com.example.humblr.sharedPrefs.SharedPrefToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
@HiltViewModel
class ProfileViewModel  @Inject constructor(private val repo: RetrofitRepository): ViewModel() {

    //Информация обо мне
    private var _meInfo: MutableStateFlow<MeItem?> = MutableStateFlow(null)
    val meInfo = _meInfo.asStateFlow()

    //Список моих жрузей
    private var _friends: MutableStateFlow<List<Children>> = MutableStateFlow(emptyList())
    val friends = _friends.asStateFlow()

    //Состояние загрузки
    private var _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    //Получить прогресс
    fun loading(){
        _isLoading.value = true
    }

    //Убрать прогресс
    fun unloading(){
        _isLoading.value = false
    }

    //Получение информации обо мне
    suspend fun getMeInfo(context: Context){
        _meInfo.value = repo.getMe(SharedPrefToken(context).getText()!!)
    }

    //Получение информации о друзьях
    suspend fun getMyFriends(context: Context){
        _friends.value = repo.getMyFiends(SharedPrefToken(context).getText()!!).data.children
    }
}