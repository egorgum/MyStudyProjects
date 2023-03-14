package com.example.weather.ui.main.AllFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.weather.MainViewModel
import com.example.weather.R
import com.example.weather.databinding.FragmentAllBinding
import com.example.weather.databinding.FragmentDetailBinding
import com.example.weather.ui.main.MainFragment.Factory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint

class AllFragment : Fragment() {

    private var _binding: FragmentAllBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels{ Factory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.allWeather.collect{
                binding.rv.adapter = RecyclerAdapter(viewModel.allWeather.value)
            }
        }
        binding.btDelete.setOnClickListener {
            viewModel.onDelete()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}