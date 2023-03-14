package com.example.humblr.ui.fragments

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.humblr.R
import com.example.humblr.databinding.FragmentAuthBinding
import com.example.humblr.sharedPrefs.SharedPrefScreen
import com.example.humblr.sharedPrefs.SharedPrefToken
import com.example.humblr.states.AuthState
import com.example.humblr.viewModels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
@AndroidEntryPoint
class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()
    //Отправка на получение авторизации
    private val getAuthResponse = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        val dataIntent = it.data ?: return@registerForActivityResult
        handleAuthResponseIntent(dataIntent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Переход на экран онбординга, если запуск впервые
        if(SharedPrefScreen(requireContext()).getFirst()){
            findNavController().navigate(R.id.action_authFragment_to_onboardFragment)
        }
        observeAuth()
        //Кнопка авторизоавться
        binding.btAuth.setOnClickListener {
            getAuthResponse.launch(viewModel.openLoginPage(requireContext()))
        }
    }

    //Наблюдение за состоянием авторизации
    private fun observeAuth() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.authState.collect { state ->
                when (state) {
                    is AuthState.LoggedIn -> {
                        Toast.makeText(requireContext(), getString(R.string.success_auth), Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_authFragment_to_navigation_home)
                    }
                    is AuthState.Error -> {
                        Toast.makeText(requireContext(),"Ошибка авторизации: ${state.errorMsg}", Toast.LENGTH_SHORT).show()
                        Log.d(TAG,state.errorMsg)
                    }
                    is AuthState.NotLoggedIn -> {
                        Log.d(TAG,"not logged in")
                    }
                }
            }
        }
    }

    //Обработка получаемой информации из авторизации
    private fun handleAuthResponseIntent(intent: Intent){
        val exception = AuthorizationException.fromIntent(intent)
        val tokenExchangeRequest = AuthorizationResponse.fromIntent(intent)?.createTokenExchangeRequest()
        when{
            exception != null -> viewModel.changeAuthStateToError(exception.error.toString())
            tokenExchangeRequest != null -> viewLifecycleOwner.lifecycleScope.launch {
                Log.d(TAG,"код: ${tokenExchangeRequest.authorizationCode.toString()}")
                val a = viewModel.getToken(tokenExchangeRequest.authorizationCode.toString())
                Log.d(TAG,"Токен: $a")
                SharedPrefToken(requireContext()).saveText(a!!)
                viewModel.changeAuthStateToLoggedIn()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}