package com.example.humblr.repository

import android.net.Uri
import com.example.humblr.states.AuthConfiguration
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationServiceConfiguration
import javax.inject.Inject
//Сервис для конфигурации запроа регистарции
class AuthService @Inject constructor(){

    //Конфигурация ссылки
    private val authConfig = AuthorizationServiceConfiguration(
        Uri.parse(AuthConfiguration.AUTH_URL),
        Uri.parse(AuthConfiguration.TOKEN_URL),
        null,
        null
    )

    //Сборка запроса
    private val requestBuilder = AuthorizationRequest.Builder(
        authConfig,
        AuthConfiguration.CLIENT_ID,
        AuthConfiguration.RESPONSE_TYPE,
        Uri.parse(AuthConfiguration.REDIRECT_URL))

    //Добавление области разрешения
    val request = requestBuilder
        .setScope(AuthConfiguration.SCOPE)
        .build()
}