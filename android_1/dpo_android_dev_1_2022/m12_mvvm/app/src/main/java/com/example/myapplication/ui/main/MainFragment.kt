package com.example.myapplication.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.FragmentMainBinding
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btSearch.setOnClickListener {
            val searchText = binding.search.text.toString()
            viewModel.onButtonClick(searchText)
        }

        binding.search.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val searchText = binding.search.text.toString()
                viewModel.onChangeText(searchText)
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collect{
                state ->
                when(state){
                    State.Start-> {
                        binding.pb.isVisible = false
                        binding.btSearch.isEnabled = true
                    }
                    State.Loading -> {
                        binding.pb.isVisible = true
                        binding.btSearch.isEnabled = false
                    }
                    State.Result -> {
                        binding.pb.isVisible = false
                        binding.btSearch.isEnabled = true
                        binding.tvResult.text = "По запросу ${binding.search.text} ничего не найдено"
                    }
                    is State.Error -> {
                        binding.pb.isVisible = false
                        binding.btSearch.isEnabled = false
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.error.collect{
                    message ->
                Snackbar.make(requireView(),message,Snackbar.LENGTH_SHORT).show()

            }
        }

    }

}