package com.example.recycler_new.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recycler_new.databinding.RickAndMortyBinding

class MyAdapter: PagingDataAdapter<com.example.recycler_new.models.Result, MyViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RickAndMortyBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding){
            name.text = item?.name
            status.text = "${item?.status} - ${item?.species}"
            locationInfo.text = "${item?.location?.name}"
            item?.let{
                Glide
                    .with(avatar.context)
                    .load(it.image)
                    .into(avatar)
            }
        }
    }
}

class MyViewHolder(val binding: RickAndMortyBinding): RecyclerView.ViewHolder(binding.root)

class  DiffUtilCallback: DiffUtil.ItemCallback<com.example.recycler_new.models.Result>(){
    override fun areContentsTheSame(oldItem: com.example.recycler_new.models.Result, newItem: com.example.recycler_new.models.Result): Boolean = oldItem.id == newItem.id
    override fun areItemsTheSame(oldItem: com.example.recycler_new.models.Result, newItem: com.example.recycler_new.models.Result): Boolean = oldItem == newItem
}
