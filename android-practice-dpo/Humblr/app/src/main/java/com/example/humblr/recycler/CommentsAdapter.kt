package com.example.humblr.recycler

import android.content.ContentValues.TAG
import android.location.Location
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.humblr.R
import com.example.humblr.data.modelComments.Children
import com.example.humblr.databinding.OneCommentBinding
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.NoSuchElementException

private const val FILE_NAME_FORMAT = "yyyy-MM-dd HH:mm:ss"
class CommentsAdapter @Inject constructor (private val values: List<Children>,
                                           private val onClick:(Children, String, String, String) -> Unit,
                                           private val onClickSave:(Boolean,String) -> Unit,
                                           private val onVote:(String,Int) -> Unit,
                                           private val goToUser:(String) -> Unit):
    RecyclerView.Adapter<MyViewHolderComments>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderComments {
        val binding = OneCommentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolderComments(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolderComments, position: Int) {
        val item = values[position]
        var liked = item.data.likes
        Log.d(TAG, "Liked = $liked")
        var saved = item.data.saved
        with(holder.binding) {
            this.tvAuthor.text = item.data.author
            this.tvText.text = item.data.body
            //Преобразование временного формата
            val formatter = SimpleDateFormat(FILE_NAME_FORMAT)

            formatter.timeZone =TimeZone.getTimeZone("GMT")
            this.tvTime.text =
                try {
                    formatter.format(item.data.created_utc!! * 1000)
                }
                catch (e:Exception){
                    "неизвестно"
                }

            //Получение начального состояния кнопок голосования
            if (liked == true){
                ivUp.setImageResource(R.drawable.ic_baseline_arrow_upward_red_24)
                ivDown.setImageResource(R.drawable.ic_baseline_arrow_upward_24)
                ivDown.tag = "white"
                ivUp.tag = "red"
            }
            if (liked == false){
                ivDown.setImageResource(R.drawable.ic_baseline_arrow_upward_red_24)
                ivUp.setImageResource(R.drawable.ic_baseline_arrow_upward_24)
                ivUp.tag = "white"
                ivDown.tag = "red"
            }
            if (liked == null){
                ivUp.setImageResource(R.drawable.ic_baseline_arrow_upward_24)
                ivDown.setImageResource(R.drawable.ic_baseline_arrow_upward_24)
                ivUp.tag = "white"
                ivDown.tag = "white"
            }

            //Получение начального состояния кнопки сохранения
            if (saved == true){
                ivSave.setImageResource(R.drawable.ic_baseline_save_red_24)
                ivSave.tag = "red"
            }
            else{
                ivSave.setImageResource(R.drawable.ic_baseline_save_24)
                ivSave.tag = "white"
            }

            //Обработка нажатия "сохранить"
            ivSave.setOnClickListener {
                saved = if(saved == true){
                    ivSave.setImageResource(R.drawable.ic_baseline_save_24)
                    onClickSave(saved!!, item.data.name!!)
                    ivSave.tag = "white"
                    false

                } else{
                    ivSave.setImageResource(R.drawable.ic_baseline_save_red_24)
                    onClickSave(saved!!, item.data.name!!)
                    ivSave.tag = "red"
                    true
                }
            }

            //Обработка нажатия "проголосовать"
            ivUp.setOnClickListener {
                if (liked == true){
                    ivUp.setImageResource(R.drawable.ic_baseline_arrow_upward_24)
                    ivUp.tag = "white"
                    onVote(item.data.name!!,0)
                    liked = null
                }
                else{
                    ivUp.setImageResource(R.drawable.ic_baseline_arrow_upward_red_24)
                    ivUp.tag = "red"
                    ivDown.tag = "white"
                    ivDown.setImageResource(R.drawable.ic_baseline_arrow_upward_24)
                    onVote(item.data.name!!,1)
                    liked = true
                }

            }
            ivDown.setOnClickListener {
                if (liked == false){
                    ivDown.setImageResource(R.drawable.ic_baseline_arrow_upward_24)
                    ivDown.tag = "white"
                    onVote(item.data.name!!,0)
                    liked = null
                }

                else{
                    ivDown.setImageResource(R.drawable.ic_baseline_arrow_upward_red_24)
                    ivDown.tag = "red"
                    ivUp.tag = "white"
                    ivUp.setImageResource(R.drawable.ic_baseline_arrow_upward_24)
                    onVote(item.data.name!!,-1)
                    liked = false
                }
            }

            //Переход к аккаунту автора
            tvAuthor.setOnClickListener {
                goToUser(item.data.author!!)
            }

            //Переход к детальному экрану о комментарии
            holder.binding.root.setOnClickListener{
                onClick(item,ivSave.tag.toString(), ivUp.tag.toString(), ivDown.tag.toString())
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
class MyViewHolderComments @Inject constructor (val binding: OneCommentBinding) : RecyclerView.ViewHolder(binding.root)