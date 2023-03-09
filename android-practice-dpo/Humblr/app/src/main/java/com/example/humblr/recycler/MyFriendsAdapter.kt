package com.example.humblr.recycler

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.humblr.data.modelFriends.Children

import com.example.humblr.databinding.OneFriendBinding
import java.util.*
import javax.inject.Inject
import kotlin.NoSuchElementException

class MyFriendsAdapter @Inject constructor(private val values: List<Children>,
                                           private val onClick:(String) -> Unit): RecyclerView.Adapter<MyViewHolderFriends>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderFriends {
        val binding = OneFriendBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolderFriends(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolderFriends, position: Int) {
        val item = values[position]
        with(holder.binding) {
            tvFriend.text = item.name
            holder.binding.root.setOnClickListener{
                onClick(item.name)
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
class MyViewHolderFriends @Inject constructor (val binding: OneFriendBinding) : RecyclerView.ViewHolder(binding.root){}