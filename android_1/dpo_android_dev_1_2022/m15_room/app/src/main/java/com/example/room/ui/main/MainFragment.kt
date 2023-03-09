package com.example.room.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.example.room.R
import com.example.room.databinding.FragmentMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }
    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels{object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val wordDao = (requireActivity().application as App).db.wordsDao()
            return MainViewModel(wordDao) as T
        }
    }}



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btAdd.setOnClickListener{
            val text = binding.etWord.text.toString()
            val regex = Regex("[^a-zA-Zа-яА-Я^-]+")
            val isPattern = !regex.containsMatchIn(text)
            if (isPattern){
                viewModel.addButton(text)
            }
            else{
                binding.result.text ="Ошибка"
            }
        }

        binding.btClear.setOnClickListener{
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.onDelete()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.allWords.collect{
                binding.result.text = it.joinToString(separator = "\r\n")
            }
        }
    }
}