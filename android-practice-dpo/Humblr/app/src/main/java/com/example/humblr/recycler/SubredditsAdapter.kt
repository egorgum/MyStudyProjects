package com.example.humblr.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.humblr.R
import com.example.humblr.data.modelSubList.Children
import com.example.humblr.databinding.OneSubredditElementBinding
import com.example.unsplash.LoadAdapter
import javax.inject.Inject

class SubredditsAdapter@Inject constructor(private val onClick:(Children, Boolean) -> Unit,
                                           private val onClick1:(Boolean, String) -> Unit,
                                           private val goToUser:(String) -> Unit): PagingDataAdapter<Children, MyViewHolder>(
    DiffUtilCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = OneSubredditElementBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        var saved = item!!.data.saved
        with(holder.binding){
            tvTitle.text = item.data.title
            tvAuthor.text = item.data.author
            tvComments.text = item.data.num_comments.toString()
            item.let{
                //Получение начального состояния кнопки сохранить пост
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

            //Обработка сохранения
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

        //Переход к детальному экрану поста
        holder.binding.root.setOnClickListener{
            onClick(item, saved)
        }

    }
}
class MyViewHolder@Inject constructor(val binding: OneSubredditElementBinding): RecyclerView.ViewHolder(binding.root)
class  DiffUtilCallback: DiffUtil.ItemCallback<Children>(){
    override fun areContentsTheSame(oldItem: Children, newItem: Children): Boolean = oldItem.data.id == newItem.data.id
    override fun areItemsTheSame(oldItem: Children, newItem: Children): Boolean = oldItem == newItem
}
