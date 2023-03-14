package com.example.humblr.ui.fragments

import android.content.ContentValues.TAG
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.humblr.R
import com.example.humblr.databinding.FragmentAuthBinding
import com.example.humblr.databinding.FragmentOneSubBinding
import com.example.humblr.databinding.OneCommentBinding
import com.example.humblr.viewModels.OneSubViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentFragment : Fragment() {

    private var _binding: OneCommentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OneSubViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = OneCommentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Получение состояния голосования, и его обработка
        val likedString: String? = arguments?.getString("liked")
        var liked:Boolean? = null
        if (likedString == "true"){
            liked = true
        }
        if (likedString == "false"){
            liked = false
        }
        if (liked == true){
            binding.ivUp.setImageResource(R.drawable.ic_baseline_arrow_upward_red_24)
            binding.ivUp.tag = "red"
            binding.ivDown.setImageResource(R.drawable.ic_baseline_arrow_upward_24)
            binding.ivDown.tag = "white"
        }
        if (liked == false){
            binding.ivDown.setImageResource(R.drawable.ic_baseline_arrow_upward_red_24)
            binding.ivDown.tag = "red"
            binding.ivUp.setImageResource(R.drawable.ic_baseline_arrow_upward_24)
            binding.ivUp.tag = "white"
        }

        //Получение состояния сохранения и его обработка
        var isSaved = arguments?.getBoolean("saved")!!
        Log.d(TAG, "Значение тега = $isSaved")
        val name = arguments?.getString("name")!!
        binding.tvTime.text = arguments?.getString("time")
        binding.tvText.text = arguments?.getString("text")
        binding.tvAuthor.text = arguments?.getString("author")
        if(isSaved){
            binding.ivSave.setImageResource(R.drawable.ic_baseline_save_red_24)
            binding.ivSave.tag = "red"
        }
        else{
            binding.ivSave.setImageResource(R.drawable.ic_baseline_save_24)
            binding.ivSave.tag = "white"
        }

        //Голосование
        binding.ivUp.setOnClickListener {
            try {
                if (liked == true) {
                    viewModel.changeVote(name = name, dir = 0, context = requireContext())
                    binding.ivUp.setImageResource(R.drawable.ic_baseline_arrow_upward_24)
                    binding.ivUp.tag = "white"
                    liked = null
                } else {
                    viewModel.changeVote(name = name, dir = 1, context = requireContext())
                    binding.ivUp.setImageResource(R.drawable.ic_baseline_arrow_upward_red_24)
                    binding.ivUp.tag = "red"
                    binding.ivUp.tag = "white"
                    binding.ivDown.setImageResource(R.drawable.ic_baseline_arrow_upward_24)
                    liked = true
                }
            }
            catch (e:Exception){
                Toast.makeText(requireContext(),getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
            }
        }
        binding.ivDown.setOnClickListener {
            try {
                if (liked == false) {
                    viewModel.changeVote(name = name, dir = 0, context = requireContext())
                    binding.ivDown.setImageResource(R.drawable.ic_baseline_arrow_upward_24)
                    binding.ivDown.tag = "white"
                    liked = null
                } else {
                    viewModel.changeVote(name = name, dir = -1, context = requireContext())
                    binding.ivDown.setImageResource(R.drawable.ic_baseline_arrow_upward_red_24)
                    binding.ivDown.tag = "red"
                    binding.ivUp.tag = "white"
                    binding.ivUp.setImageResource(R.drawable.ic_baseline_arrow_upward_24)
                    liked = false
                }
            }
            catch (e:Exception){
                Toast.makeText(requireContext(),getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
            }
        }


        //Кнопка сохранения
        binding.ivSave.setOnClickListener {
            try {
                isSaved = if (isSaved) {
                    viewModel.changeOnUnSaved(context = requireContext(), id = name)
                    binding.ivSave.setImageResource(R.drawable.ic_baseline_save_24)
                    binding.ivSave.tag = "white"
                    false
                } else {
                    viewModel.changeOnSaved(context = requireContext(), id = name)
                    binding.ivSave.setImageResource(R.drawable.ic_baseline_save_red_24)
                    binding.ivSave.tag = "red"
                    true
                }
            }
            catch (e:Exception){
                Toast.makeText(requireContext(),getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
            }
        }

        //Переход к автору
        binding.tvAuthor.setOnClickListener {
            val bundle = Bundle().apply {
                putString("name", binding.tvAuthor.text.toString())
            }
            findNavController().navigate(R.id.userFragment, bundle)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}