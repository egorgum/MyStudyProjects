package com.example.recycler.ui.main

import android.content.ClipData
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.recycler.R
import com.example.recycler.databinding.FragmentMainBinding
import com.example.recycler.models.Photo
import com.example.recycler.secondFragment.PhotoFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MainFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels{Factory()}
    private val myAdapter = MyAdapter { photo -> onItemClick(photo) }

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rv.adapter = myAdapter
        viewModel.pagingPhotos.onEach {
            myAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
    private fun onItemClick(item: Photo){
        val bundle = Bundle().apply {
            putString("photo_info",item.img_src)
        }
        findNavController().navigate(R.id.photoFragment,bundle)

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}