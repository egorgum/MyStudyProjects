package com.example.practice_16.ui.main


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.practice_16.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {
    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var binding: FragmentMainBinding

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory
    private val viewModel: MainViewModel by viewModels{mainViewModelFactory}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btRefresh.setOnClickListener{
            viewModel.reloadUsefulActivity()
            //binding.message.text = viewModel.uaFlow.value?.activity
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uaFlow.collect{
                when(it){
                    null -> binding.message.text = "Нажимте кнопку или включите иинтернет"
                     else -> binding.message.text = viewModel.uaFlow.value?.activity
                }
            }
        }



    }

}