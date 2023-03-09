package com.example.QuizAPP

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.QuizAPP.databinding.FragmentQuizBinding
import com.example.QuizAPP.quiz.QuizStorage
import java.util.*

class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentQuizBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ObjectAnimator.ofFloat(binding.firstQuestion, "alpha" ,
            0f,
            1f).apply {
            duration = 1000
            start()
        }

        binding.buttonPush.setOnClickListener{


            val list = mutableListOf<Int>()
            when(binding.firstGroup.checkedRadioButtonId){
                R.id.firstGroupOne -> list.add(0)
                R.id.firstGroupTwo -> list.add(1)
                R.id.firstGroupThree -> list.add(2)
                R.id.firstGroupFour -> list.add(3)
            }
            when(binding.secondGroup.checkedRadioButtonId){
                R.id.secondGroupOne -> list.add(0)
                R.id.secondGroupTwo -> list.add(1)
                R.id.secondGroupThree -> list.add(2)
                R.id.secondGroupFour -> list.add(3)
            }
            when(binding.thirdGroup.checkedRadioButtonId){
                R.id.thirdGroupOne -> list.add(0)
                R.id.thirdGroupTwo -> list.add(1)
                R.id.thirdGroupThree -> list.add(2)
                R.id.thirdGroupFour -> list.add(3)
            }


            val questionList = if (Locale.getDefault().language == "ru"){
                QuizStorage.answer(QuizStorage.getQuiz(QuizStorage.Locale.Ru),list)
            } else{
                QuizStorage.answer(QuizStorage.getQuiz(QuizStorage.Locale.En),list)
            }
            val bundle = Bundle().apply {
                putString("MyArg", questionList)
            }

            findNavController().navigate(R.id.action_quizFragment_to_resultFragment,bundle)
        }

        binding.buttonBack.setOnClickListener{
            findNavController().navigate(R.id.action_quizFragment_to_blankFragment2)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}