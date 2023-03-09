package com.example.practice_18.ui.main

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.practice_18.databinding.OnePhotoBinding
import com.example.practice_18.entity.UriEntity

class PhotoAdapter(private val values: List<UriEntity>): Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = OnePhotoBinding.inflate(LayoutInflater.from(parent.context))
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = values[position]
        with(holder.binding) {
            date.text = item.date
            item.let {
                Glide
                    .with(photo.context)
                    .load(Uri.parse(item.uri))
                    .into(photo)
            }
        }
    }

    override fun getItemCount(): Int {
        return try {
            values.size
        } catch(e: NoSuchElementException) {
            0
        }
    }
}
class MyViewHolder(val binding: OnePhotoBinding) : ViewHolder(binding.root)