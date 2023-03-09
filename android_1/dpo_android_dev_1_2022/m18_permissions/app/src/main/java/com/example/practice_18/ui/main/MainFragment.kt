package com.example.practice_18.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.practice_18.R
import com.example.practice_18.data.App
import com.example.practice_18.databinding.FragmentMainBinding
import com.example.practice_18.entity.UriEntity
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels{object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val uriDao = (requireActivity().application as App).db.dao()
            return MainViewModel(uriDao) as T
        }
    }}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.allUri.collect{
                binding.rw.adapter = PhotoAdapter(viewModel.allUri.value)
            }
        }

        binding.openCamera.setOnClickListener{
            findNavController().navigate(R.id.secondFragment)
        }
        binding.clear.setOnClickListener{
            viewModel.onDelete()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}