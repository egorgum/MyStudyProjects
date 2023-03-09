package com.example.weather.ui.main.AllFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.databinding.OneWeatherBinding
import com.example.weather.ui.main.Room.DataBaseWeather
import javax.inject.Inject

class RecyclerAdapter @Inject constructor(private val values: List<DataBaseWeather>): RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = OneWeatherBinding.inflate(LayoutInflater.from(parent.context))
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = values[position]
        with(holder.binding) {
            tvInfo.text = item.toString()}
    }

    override fun getItemCount(): Int {
        return try {
            values.size
        } catch(e: NoSuchElementException) {
            0
        }
    }
}
class MyViewHolder(val binding: OneWeatherBinding) : RecyclerView.ViewHolder(binding.root)