package com.example.cactom.ui.main



import android.os.Bundle

import androidx.fragment.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.cactom.R
import com.example.cactom.databinding.FragmentMainBinding
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.TimeUnit


const val TIME = "time"
const val BOOL = "bool"
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val scope = CoroutineScope(Job() + Dispatchers.Main)
    var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fun putDate(){
            binding.digital.text = viewModel.convToString()
            binding.analog.hours = TimeUnit.MILLISECONDS.toHours(viewModel.currentTime).toInt()
            binding.analog.minutes = TimeUnit.MILLISECONDS.toMinutes(viewModel.currentTime).toInt()
            binding.analog.seconds = TimeUnit.MILLISECONDS.toSeconds(viewModel.currentTime).toInt()
        }

        suspend fun cor() = scope.launch {
            viewModel.isStarted = true
            binding.btStart.text = getText(R.string.stop)
            while (viewModel.isStarted){
                viewModel.currentTime += 1000
                putDate()
                delay(1000)
            }
        }

        if (savedInstanceState != null) {
            with(savedInstanceState) {
                viewModel.currentTime = getLong(TIME)
                if (getBoolean(BOOL)){
                    job = scope.launch{
                        cor()
                    }
                }
                else{
                   putDate()
                }
            }
        }

        binding.btReset.setOnClickListener {
            job?.cancel()
            viewModel.isStarted = false
            viewModel.currentTime = 0
            binding.analog.hours = 0
            binding.analog.minutes = 0
            binding.analog.seconds = 0
            binding.digital.text = viewModel.convToString()
            binding.btStart.text = getText(R.string.start)
        }

        binding.btStart.setOnClickListener {
            if(!viewModel.isStarted){
                job = scope.launch {
                    cor()
                }
            }
            else{
                job?.cancel()
                binding.btStart.text = getText(R.string.start)
                viewModel.isStarted = false

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(TIME, viewModel.currentTime)
        outState.putBoolean(BOOL,viewModel.isStarted)


    }
}