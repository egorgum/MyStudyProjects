package com.example.humblr.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.humblr.R
import com.example.humblr.data.modelSubList.Children
import com.example.humblr.databinding.FragmentSearchBinding
import com.example.humblr.recycler.SubredditsAdapter
import com.example.humblr.states.PostsStates
import com.example.humblr.viewModels.SearchViewModel
import com.example.unsplash.LoadAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()
    private val myAdapter = SubredditsAdapter ({sub, saved -> onItemClick(sub, saved)}, {saved, name ->onSaveClick(saved,name)}, {author -> goToUser(author)})

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rv.adapter = myAdapter.withLoadStateFooter(LoadAdapter())
        //обработка состояний получения списка
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
        //Получение списка
        viewModel.paging(requireContext(),PostsStates.Search, arguments?.getString("search")!!)
        viewModel.pagingSubs?.onEach {pd ->
            myAdapter.submitData(pd)
        }?.launchIn(viewLifecycleOwner.lifecycleScope)

        //Обработка состояния прогресса
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

    //Переход к посту
    private fun onItemClick(item: Children, saved: Boolean){
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
            Toast.makeText(requireContext(),getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
        }
    }

    //Сохранение
    private fun onSaveClick(bool:Boolean,
                            id:String,) {
        if(bool){
            viewModel.changeOnUnSaved(context = requireContext(), id = id)
        }
        else{
            viewModel.changeOnSaved(context = requireContext(), id = id)
        }
    }

    //Переход к пользователю
    private fun goToUser(name: String){
        val bundle = Bundle().apply {
            putString("name", name)
        }
        findNavController().navigate(R.id.userFragment,bundle)
    }

}