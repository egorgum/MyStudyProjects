package com.example.humblr.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.humblr.R
import com.example.humblr.databinding.FragmentOnboardBinding
import com.example.humblr.sharedPrefs.SharedPrefScreen
import com.example.humblr.viewModels.OnboardViewModel


import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardFragment : Fragment() {

    private var _binding: FragmentOnboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OnboardViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!SharedPrefScreen(requireContext()).getFirst()){
            findNavController().navigate(R.id.authFragment)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SharedPrefScreen(requireContext()).saveFirst(token = false)

        //Далее
        fun navNext(){
            when(viewModel.title.value){
                R.string.first_recommendation -> {
                    viewModel.title.value = R.string.second_recommendation
                }
                R.string.second_recommendation -> {
                    viewModel.title.value = R.string.third_recommendation
                }
                R.string.third_recommendation -> {
                    findNavController().navigate(R.id.action_onboardFragment_to_authFragment)
                }
            }
        }

        //нажатие на кнопку далее
        binding.btNext.setOnClickListener {
            navNext()
        }

        //Наблюдение за состоянием title
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.title.collect{
                binding.tvMain.text = getText(viewModel.title.value)
                when(it){
                    R.string.first_recommendation -> {
                        binding.tvDescription.text = getText(R.string.first_description)
                        binding.ivPicture.setImageResource(R.drawable.first_onboarding)
                    }
                    R.string.second_recommendation -> {
                        binding.tvDescription.text = getText(R.string.second_description)
                        binding.ivPicture.setImageResource(R.drawable.second_onboarding)
                    }
                    R.string.third_recommendation -> {
                        binding.tvDescription.text = getText(R.string.third_description)
                        binding.ivPicture.setImageResource(R.drawable.third_onboarding)
                        binding.btNext.text =  getText(R.string.finish)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}