package com.nyandori.goodnightblessings

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.nyandori.goodnightblessings.databinding.MessageItemRecyclerRowBinding

class MyAdapter (private val messageList: List<GoodNightMessageModel>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {



    class MyViewHolder(private val binding: MessageItemRecyclerRowBinding): RecyclerView.ViewHolder(binding.root){
       fun bindData(message: GoodNightMessageModel){
           binding.apply {
           binding.dateTextView.text = message.date
               binding.textTitle.text = message.good_night_message.title


               root.setOnClickListener {

                   GoodNightMessageActivity.good_night_message = message.good_night_message
                   val intent = Intent(root.context, GoodNightMessageActivity::class.java)
                   root.context.startActivity(intent)

               }
           }
       }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = MessageItemRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {


        return messageList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(messageList[position])
    }


}