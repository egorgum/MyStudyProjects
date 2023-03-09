package com.example.humblr.ui.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
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
import com.example.humblr.databinding.FragmentSearchBinding
import com.example.humblr.databinding.FragmentUserBinding
import com.example.humblr.recycler.CommentsAdapter
import com.example.humblr.states.FriendState
import com.example.humblr.viewModels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
private const val FILE_NAME_FORMAT = "yyyy-MM-dd HH:mm:ss"
@AndroidEntryPoint
class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Обработка получаемых аргументов и получение информации
        val arg = arguments?.getString("name")!!
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            try {
                viewModel.loading()
                Log.d(TAG, "arg = $arg")
                viewModel.getUser(arg, requireContext())
                viewModel.getComments(arg, requireContext())
                Log.d(TAG, "COM=${viewModel.comments.value}")
                binding.rv.adapter = CommentsAdapter(viewModel.comments.value,
                    onClick = { sub, tag, tagUp, tagDown -> onItemClick(sub,tag, tagUp, tagDown)},
                    onClickSave = {bool,string -> onSaveClick(bool,string)},
                    onVote = {name,dir -> onVoteClick(name,dir)},
                    goToUser = {author -> goToUser(author)})
                if (viewModel.userInfo.value!!.data.is_friend){
                    viewModel.startedFriendState()
                }
                Glide
                    .with(binding.ivAvatar.context)
                    .load(viewModel.userInfo.value!!.data.snoovatar_img)
                    .into(binding.ivAvatar)
                binding.tvName.text = viewModel.userInfo.value!!.data.name
                binding.tvGold.text =
                    if (viewModel.userInfo.value!!.data.is_gold) {
                        getString(R.string.gold)
                    } else {
                        getString(R.string.not_gold)
                    }
                binding.tvKarma.text = "${getString(R.string.karma)} ${viewModel.userInfo.value!!.data.total_karma}"
                viewModel.unloading()
            }
            catch (e:Exception){
                Toast.makeText(requireContext(),getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
                Log.d(TAG, "Ошибка в фрагменте юсера: $e")
            }

            //Состояние отношений с пользователем
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.friendState.collect{
                    when(it){
                        FriendState.Friend -> binding.addFriend.text = getString(R.string.delete_a_friend)
                        FriendState.NotFriend -> binding.addFriend.text = getString(R.string.add_to_friends)
                    }
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
                            binding.rv.visibility = View.VISIBLE
                            binding.progressBar.visibility = View.GONE
                        }
                    }
                }
            }

            //Добавить в друзья или удалить
            binding.addFriend.setOnClickListener{
                viewLifecycleOwner.lifecycleScope.launch {
                    try {

                        if (viewModel.friendState.value == FriendState.NotFriend) {
                            viewModel.addToFriend(name = viewModel.userInfo.value!!.data.name, context = requireContext())
                        }
                        else{
                            viewModel.deleteFriend(name = viewModel.userInfo.value!!.data.name, context = requireContext())
                        }
                    }
                    catch (e:Exception){
                        Toast.makeText(requireContext(),getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
                    }
                }

            }
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

    //Сохранение комментария
    private fun onSaveClick(bool:Boolean,
                            id:String,) {
        if(bool){
            viewModel.changeOnUnSaved(context = requireContext(), id = id)
        }
        else{
            viewModel.changeOnSaved(context = requireContext(), id = id)
        }
    }

    //Проголосовать за комментарий
    private fun onVoteClick(name:String, dir:Int){
        viewModel.changeVote(name = name, dir = dir, context = requireContext())
    }

    //Переход к автору комментария
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