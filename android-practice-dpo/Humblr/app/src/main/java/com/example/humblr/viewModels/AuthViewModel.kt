package com.example.humblr.viewModels

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.ViewModel
import com.example.humblr.repository.AuthService
import com.example.humblr.repository.RetrofitRepository
import com.example.humblr.states.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.openid.appauth.AuthorizationService
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val service: AuthService,
                                        private val repo: RetrofitRepository): ViewModel() {
    //Состояние авторизации
    private val _authState = MutableStateFlow<AuthState>(AuthState.NotLoggedIn)
    val authState = _authState.asStateFlow()

    //Открытие страницы авторизации
    fun openLoginPage(context: Context): Intent {
        val customTabsIntent = CustomTabsIntent.Builder().build()
        val authRequest = service.request

        return AuthorizationService(context).getAuthorizationRequestIntent(
            authRequest,
            customTabsIntent
        )
    }

    //Сменить состояние на "Ошибка"
    fun changeAuthStateToError(str:String){
        _authState.value = AuthState.Error(str)
    }

    //Сменить состояние на "Зарегестрирован"
    fun changeAuthStateToLoggedIn(){
        _authState.value = AuthState.LoggedIn
    }

    //Получение токена
    suspend fun getToken(code: String):String?{
        return try {
            Log.d(ContentValues.TAG, "получение токена началось")
            Log.d(ContentValues.TAG, "code: $code")
            val a = repo.getTokenRepo(code)
            Log.d(ContentValues.TAG, "токен: ${a.access_token}")
            a.access_token
        } catch (e:Exception) {
            _authState.value = AuthState.Error(errorMsg = "ошибка получения токена: $e")
            null
        }
    }

}