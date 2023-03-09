package com.example.weather.ui.main.MainFragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.weather.R
import com.example.weather.databinding.FragmentMainBinding
import com.example.weather.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels{ Factory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sp = SharedPrefs(requireContext())

        binding.tvLastCity.text = sp.getText()

        binding.btSearch.setOnClickListener {
            val text = binding.etSearch.text.toString()
            sp.saveText(text)
            binding.tvLastCity.text = sp.getText()
            start(text)
        }

        binding.tvLastCity.setOnClickListener {
            binding.etSearch.setText(binding.tvLastCity.text)
        }

        binding.btCities.setOnClickListener {
            findNavController().navigate(R.id.allFragment)
        }
    }

    private fun start(text: String){
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.loadWeather(text.uppercase().trim())
            viewModel.flag.collect{
                when(it){
                    null -> {}
                    false -> Toast.makeText(requireContext(), "Что-то пошло не так", Toast.LENGTH_SHORT).show()
                    true -> {
                        findNavController().navigate(R.id.detailFragment, viewModel.bundle.value)
                        viewModel.bundle.value = null
                        viewModel.flag.value = null
                    }
                }
            }
        }
    }
}