package com.example.humblr.ui.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.humblr.R
import com.example.humblr.databinding.FragmentOneSubBinding
import com.example.humblr.recycler.CommentsAdapter
import com.example.humblr.viewModels.OneSubViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
private const val FILE_NAME_FORMAT = "yyyy-MM-dd HH:mm:ss"
@AndroidEntryPoint
class OneSubFragment : Fragment() {

    private var _binding: FragmentOneSubBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OneSubViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOneSubBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var isSaved = arguments?.getBoolean("saved")!!
        val namePost ="t3_${arguments?.getString("id")}"
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            //Получение информации и посте и её обработка
            try {
                viewModel.loading()
                viewModel.getComments(arguments?.getString("id").toString(), requireContext())
                binding.rv.adapter = CommentsAdapter(viewModel.comments.value,
                    onClick = { sub, tag, tagUp, tagDown -> onItemClick(sub,tag, tagUp, tagDown)},
                    onClickSave = {bool,string -> onSaveClick(bool,string)},
                    onVote = {name,dir -> onVoteClick(name,dir)},
                    goToUser = {author -> goToUser(author)})
                binding.tvTitle.text = arguments?.getString("title")
                binding.tvText.text = arguments?.getString("selfText")
                binding.tvAuthor.text = arguments?.getString("author")
                Glide
                    .with(binding.ivPhoto.context)
                    .load(arguments?.getString("photo"))
                    .into(binding.ivPhoto)
                if(isSaved){
                    binding.ivSave.setImageResource(R.drawable.ic_baseline_check_24)
                }
                else{
                    binding.ivSave.setImageResource(R.drawable.ic_baseline_add_24)
                }
                viewModel.unloading()
            }
            catch (e:Exception){
                Toast.makeText(requireContext(),getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
            }

            //Наблюдение за состоянием прогресса
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.isLoading.collect{
                    when(it){
                        true -> {
                            binding.scroll.visibility = View.GONE
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        false ->{
                            binding.progressBar.visibility = View.GONE
                            binding.scroll.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

        //Сохранение
        binding.ivSave.setOnClickListener {
            try {
                isSaved = if (isSaved) {
                    viewModel.changeOnUnSaved(context = requireContext(), id = namePost)
                    binding.ivSave.setImageResource(R.drawable.ic_baseline_add_24)
                    false
                } else {
                    viewModel.changeOnSaved(context = requireContext(), id = namePost)
                    binding.ivSave.setImageResource(R.drawable.ic_baseline_check_24)
                    true
                }
            }
            catch (e:Exception){
                Toast.makeText(requireContext(),getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
            }
        }

        //Поделиться
        binding.ivShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            val shareBody = "https://www.reddit.com${arguments?.getString("link")}"
            intent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(intent, getString(R.string.share_using)))
        }
        binding.tvAuthor.setOnClickListener {
            val bundle = Bundle().apply {
                putString("name", binding.tvAuthor.text.toString())
            }
            findNavController().navigate(R.id.userFragment, bundle)
        }

    }
    //Переход к комментарию
    private fun onItemClick(item: com.example.humblr.data.modelComments.Children,
                            tag:String, tagUp:String, tagDown: String ){
        try {
            val bundle = Bundle().apply {
                putString("author", item.data.author )
                putString("text", item.data.body)
                val formatter = SimpleDateFormat(FILE_NAME_FORMAT)
                formatter.timeZone = TimeZone.getTimeZone("GMT")
                val time = formatter.format(item.data.created_utc!! * 1000)
                putString("time", time)
                val saved = tag != "white"
                putBoolean("saved",saved)
                putString("name",item.data.name)
                if (tagUp == "red"){
                    putString("liked","true")
                }
                if (tagDown == "red"){
                    putString("liked","false")
                }
            }
            findNavController().navigate(R.id.commentFragment, bundle)
        }
        catch (e:Exception){
            Toast.makeText(requireContext(),getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
        }
    }

    //Сохранить
    private fun onSaveClick(bool:Boolean,
                            id:String,) {
        if(bool){
            viewModel.changeOnUnSaved(context = requireContext(), id = id)
        }
        else{
            viewModel.changeOnSaved(context = requireContext(), id = id)
        }
    }

    //Проголосовать
    private fun onVoteClick(name:String, dir:Int){
        viewModel.changeVote(name = name, dir = dir, context = requireContext())
    }
    //Переход к пользователю
    private fun goToUser(name: String){
        val bundle = Bundle().apply {
            putString("name", name)
        }
        findNavController().navigate(R.id.userFragment,bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}