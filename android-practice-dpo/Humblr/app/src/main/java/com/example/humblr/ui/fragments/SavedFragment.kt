package com.example.humblr.ui.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.humblr.R
import com.example.humblr.data.modelSubList.Children
import com.example.humblr.databinding.FragmentSavedBinding
import com.example.humblr.recycler.CommentsAdapter
import com.example.humblr.recycler.SavedPostsAdapter
import com.example.humblr.states.SavedStates
import com.example.humblr.viewModels.SavedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
private const val FILE_NAME_FORMAT = "yyyy-MM-dd HH:mm:ss"
@AndroidEntryPoint
class SavedFragment : Fragment() {

    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SavedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            try {
                //Получение моего имени и выбор категории получаемого списка
                viewModel.getUserName(requireContext())
                if(binding.swSaved.isChecked) {
                    viewModel.getSavedPosts(requireContext(), viewModel.username!!)

                }
                else{
                    viewModel.getSavedComments(requireContext(), viewModel.username!!)
                }
                observeState()
            }
            catch (e:Exception){
                Toast.makeText(requireContext(),getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
            }
        }

        //Наблюдение за состоянием прогресса
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.isLoading.collect{
                when(it){
                    true -> {
                        binding.rv.visibility = View.GONE
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    false ->{
                        binding.progressBar.visibility = View.GONE
                        binding.rv.visibility = View.VISIBLE
                    }
                }
            }
        }

        //Перключатель
        binding.swSaved.setOnCheckedChangeListener{ listenet, _ ->
            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    if (listenet.isChecked) {
                        viewModel.getSavedPosts(requireContext(), viewModel.username!!)
                        binding.swSaved.text = getText(R.string.posts)
                    } else {
                        viewModel.getSavedComments(requireContext(), viewModel.username!!)
                        binding.swSaved.text = getText(R.string.comments)
                        binding.swSaved.setTextColor(resources.getColor(R.color.purple_500))
                    }
                }
                catch (e:Exception){
                    Toast.makeText(requireContext(),getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //Наблюдение за изменением категории списка
    private fun observeState(){
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                try {
                    when (it) {
                        SavedStates.Comments -> {
                            viewModel.loading()
                            binding.rv.adapter = CommentsAdapter(
                                viewModel.comments.value!!,
                                onClick = { sub, tag, tagUp, tagDown ->
                                    onItemClick(
                                        sub,
                                        tag,
                                        tagUp,
                                        tagDown
                                    )
                                },
                                onClickSave = { bool, string -> onSaveClick(bool, string) },
                                onVote = { name, dir -> onVoteClick(name, dir) },
                                goToUser = { author -> goToUser(author) })
                            viewModel.unloading()
                        }
                        SavedStates.Posts -> {
                            viewModel.loading()
                            binding.rv.adapter = SavedPostsAdapter(
                                viewModel.posts.value!!,
                                onClick = { sub, saved -> onItemClick1(sub, saved) },
                                onClick1 = { saved, name -> onSaveClick(saved, name) },
                                goToUser = { author -> goToUser(author) })
                            viewModel.unloading()
                        }
                        else -> {}
                    }
                }
                catch (e:Exception){
                    Toast.makeText(requireContext(),getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //Переход к посту
    private fun onItemClick1(item: Children, saved: Boolean){
        try {
            val bundle = Bundle().apply {
                putString("title", item.data.title )
                putString("photo", item.data.url)
                putString("author", item.data.author)
                putString("selfText", item.data.selftext)
                putString("link", item.data.permalink)
                putString("id", item.data.id)
                putBoolean("saved", saved)
            }
            findNavController().navigate(R.id.oneSubFragment, bundle)
        }
        catch (e:Exception){
            Log.d(ContentValues.TAG, saved.toString())
            Log.d(ContentValues.TAG,"Ошибка при переходе к посту: $e")
            Toast.makeText(requireContext(),getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
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
    //Переход к юзеру
    private fun goToUser(name: String){
        val bundle = Bundle().apply {
            putString("name", name)
        }
        findNavController().navigate(R.id.userFragment,bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}