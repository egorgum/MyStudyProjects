package com.example.humblr.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.humblr.R
import com.example.humblr.databinding.OneSubredditElementBinding
import javax.inject.Inject

class SavedPostsAdapter @Inject constructor(private val values: List<com.example.humblr.data.modelSubList.Children>,
                                            private val onClick:(com.example.humblr.data.modelSubList.Children, Boolean) -> Unit,
                                            private val onClick1:(Boolean, String) -> Unit,
                                            private val goToUser:(String) -> Unit): RecyclerView.Adapter<MyViewHolderSavedPosts>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderSavedPosts {
        val binding = OneSubredditElementBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolderSavedPosts(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolderSavedPosts, position: Int) {
        val item = values[position]
        var saved = item.data.saved
        with(holder.binding){
            tvTitle.text = item.data.title
            tvAuthor.text = item.data.author
            tvComments.text = item.data.num_comments.toString()
            item.let{

                //Получение первоначального состояния кнопки "Сохранить пост"
                Glide
                    .with(ivImage.context)
                    .load(it.data.url)
                    .into(ivImage)
                if (saved){
                    ivSubscribe.setImageResource(R.drawable.ic_baseline_check_24)
                }
                else{
                    ivSubscribe.setImageResource(R.drawable.ic_baseline_add_24)
                }

            }

            //Переход к автору
            tvAuthor.setOnClickListener {
                goToUser(item.data.author!!)
            }

            //Обработка нажатия кнопки "Сохранить пост"
            ivSubscribe.setOnClickListener {
                saved = if(saved){
                    ivSubscribe.setImageResource(R.drawable.ic_baseline_add_24)
                    onClick1(saved, item.data.name)
                    false

                } else{
                    ivSubscribe.setImageResource(R.drawable.ic_baseline_check_24)
                    onClick1(saved, item.data.name)
                    true
                }
            }
        }

        //Переход к детальной и нформации о посте
        holder.binding.root.setOnClickListener{
            onClick(item, saved)
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
class MyViewHolderSavedPosts @Inject constructor (val binding: OneSubredditElementBinding) : RecyclerView.ViewHolder(binding.root)