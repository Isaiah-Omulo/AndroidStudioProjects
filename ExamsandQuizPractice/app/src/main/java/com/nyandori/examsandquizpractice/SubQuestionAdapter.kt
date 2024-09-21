package com.nyandori.examsandquizpractice

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nyandori.examsandquizpractice.databinding.SubQuestionItemRecyclerRowBinding

class SubQuestionAdapter(private var questionsModelList: List<SubList> )
    : RecyclerView.Adapter<SubQuestionAdapter.MyViewHolder>()
{
    class MyViewHolder(private val binding: SubQuestionItemRecyclerRowBinding): RecyclerView.ViewHolder(binding.root) {
       fun bind(model: SubList){
           binding.apply {
               subTitle.text = model.subTitle


               root.setOnClickListener {
                   QuizDisplayBufferActivity.link = model.subLink
                   QuizDisplayBufferActivity.numberOfSections = model.numberOfSections.toInt()
                   QuizDisplayBufferActivity.numberOfPages= model.numberOfPages.toInt()

                   val intent = Intent(root.context,  QuizDisplayBufferActivity::class.java)
                   root.context.startActivity(intent)
               }

           }

       }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = SubQuestionItemRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return questionsModelList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(questionsModelList[position])
    }
}