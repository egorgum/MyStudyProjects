package com.example.humblr.ui.fragments

import android.content.ContentValues.TAG
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
import androidx.paging.LoadState
import com.example.humblr.R
import com.example.humblr.data.modelSubList.Children
import com.example.humblr.databinding.FragmentHomeBinding
import com.example.humblr.recycler.SubredditsAdapter
import com.example.humblr.states.PostsStates
import com.example.humblr.viewModels.HomeViewModel
import com.example.unsplash.LoadAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private val myAdapter = SubredditsAdapter (
        onClick = {sub, saved -> onItemClick(sub,saved)},
        onClick1 = {saved, name ->onSaveClick(saved,name)},
        goToUser = {author -> goToUser(author)}
        )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            //Обработка состояний получения постов
            myAdapter.addLoadStateListener {
                if (it.refresh == LoadState.Loading){
                    viewModel.loading()
                }
                else{
                    viewModel.unloading()
                }
                if (it.refresh is LoadState.Error){
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.something_went_wrong),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            binding.rv.adapter = myAdapter.withLoadStateFooter(LoadAdapter())

            //Переключение состояния списка на Новые
            viewModel.changeSwitchState(PostsStates.New)
        }
        catch (e:Exception){
            Toast.makeText(requireContext(),getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
        }
        //Поиск
        binding.ivSearch.setOnClickListener {
            val a = binding.etSearch.text.toString()
            val bundle = Bundle().apply {
                putString("search", a)
            }
            findNavController().navigate(R.id.searchFragment, bundle)
        }

        //Обработка переключателя
        binding.sSort.setOnCheckedChangeListener{ listenet, _ ->
            if(listenet.isChecked){
                binding.sSort.text = getText(R.string.popular)
                binding.sSort.setTextColor(resources.getColor(R.color.purple_500))
                viewModel.changeSwitchState(PostsStates.Popular)
            }
            else{
                binding.sSort.text = getText(R.string.newSort)
                binding.sSort.setTextColor(resources.getColor(R.color.purple_200))
                viewModel.changeSwitchState(PostsStates.New)
            }
        }

        //Наблюдение ща состоянием списка
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.switchState.collect{
                try {
                    viewModel.paging(requireContext(), it)
                    viewModel.pagingSubs ?. onEach { pd ->
                        myAdapter.submitData(pd)
                    }?.launchIn(viewLifecycleOwner.lifecycleScope)
                } catch (e: Exception) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.something_went_wrong),
                        Toast.LENGTH_SHORT
                    ).show()
                }


            }
        }

        //Наблюдение за стостоянием пргресса
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
    }

    //Переход к детальной информации о посте
    private fun onItemClick(item: Children,saved: Boolean){
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
            findNavController().navigate(R.id.action_navigation_home_to_oneSubFragment, bundle)
        }
        catch (e:Exception){
            Log.d(TAG, saved.toString())
            Log.d(TAG,"Ошибка при переходе к посту: $e")
            Toast.makeText(requireContext(),getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
        }
    }

    //Переход к пользователю
    private fun goToUser(name: String){
        val bundle = Bundle().apply {
            putString("name", name)
        }
        findNavController().navigate(R.id.userFragment,bundle)
    }

    //Сохранить или удалить сохранение
    private fun onSaveClick(bool:Boolean,
                            id:String,) {
        Log.d(TAG, "Время: ${Locale.getDefault()}")
        if(bool){
            viewModel.changeOnUnSaved(context = requireContext(), id = id)
        }
        else{
            viewModel.changeOnSaved(context = requireContext(), id = id)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}