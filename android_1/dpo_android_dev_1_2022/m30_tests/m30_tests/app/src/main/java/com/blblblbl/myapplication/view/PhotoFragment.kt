package com.blblblbl.myapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blblblbl.myapplication.R
import com.blblblbl.myapplication.databinding.FragmentMainBinding
import com.blblblbl.myapplication.databinding.FragmentPhotoBinding
import com.bumptech.glide.Glide


class PhotoFragment : Fragment() {
   lateinit var binding: FragmentPhotoBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotoBinding.inflate(layoutInflater)
        arguments?.getString(MainFragment.PHOTO_KEY).let { uri->
            Glide.with(binding.ivPhoto.context).load(uri).into(binding.ivPhoto)
        }
        return binding.root
    }


}