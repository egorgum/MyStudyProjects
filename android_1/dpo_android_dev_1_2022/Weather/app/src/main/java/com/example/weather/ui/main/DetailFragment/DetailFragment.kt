package com.example.weather.ui.main.DetailFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weather.R
import com.example.weather.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {


    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvCityName.text = arguments?.getString("cityName")
        binding.tvTemp.text = "The temperature is ${arguments?.getFloat("temp")} °C"
        binding.tvFeels.text = "Feels like ${arguments?.getFloat("feels")} °C"
        binding.tvDescription.text = "${arguments?.getString("description")}"
        binding.tvWindSpeed.text = "Wind speed ${arguments?.getFloat("windSpeed")} м/с"

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}