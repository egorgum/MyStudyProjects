package com.example.recycler.ui.main

import android.graphics.Movie
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recycler.databinding.PhotomarsBinding
import com.example.recycler.models.Photo

class MyAdapter(private val onClick:(Photo) -> Unit): PagingDataAdapter<Photo, MyViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = PhotomarsBinding.inflate(LayoutInflater.from(parent.context))
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding){
            cameraName.text = item?.camera?.name
            date.text = "Дата:"+"${item?.earth_date}"
            item?.let{
                Glide
                    .with(photo.context)
                    .load(it.img_src)
                    .into(photo)
            }
        }
        holder.binding.root.setOnClickListener{
            item?.let {
                onClick(item)
            }
        }
    }
}
class  DiffUtilCallback: DiffUtil.ItemCallback<Photo>(){
    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean = oldItem.id == newItem.id


    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean = oldItem == newItem
}

class MyViewHolder(val binding: PhotomarsBinding):RecyclerView.ViewHolder(binding.root)