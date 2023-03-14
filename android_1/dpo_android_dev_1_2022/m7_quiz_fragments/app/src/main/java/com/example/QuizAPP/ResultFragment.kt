package com.example.QuizAPP

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.fragment.findNavController
import com.example.QuizAPP.databinding.FragmentResultBinding


class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ObjectAnimator.ofFloat(binding.buttonZero, View.ROTATION,
            0f,
            720f).apply {
            duration = 1000
            start()
        }

        ObjectAnimator.ofArgb(binding.result, "textColor",
            Color.parseColor("#FF0000"),
            Color.parseColor("#0000FF")).apply {
            duration = 1000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
            start()
        }

        binding.result.text = arguments?.getString("MyArg")
        binding.buttonZero.setOnClickListener{
            findNavController().navigate(R.id.quizFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}