package com.nyandori.examsandquizpractice

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nyandori.examsandquizpractice.databinding.QuestionItemRecyclerRowBinding

class MainQuestionAdapter(private val questionModelList: List<QuestionModel>)
    : RecyclerView.Adapter<MainQuestionAdapter.MyViewHolder>()
{
    class MyViewHolder (private val binding: QuestionItemRecyclerRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(model: QuestionModel){
            binding.apply {
                mainTitle.text = model.mainTitle

                root.setOnClickListener {
                    QuestionActivity.subList = model.subList
                    val intent = Intent(root.context, QuestionActivity::class.java)
                    root.context.startActivity(intent)
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = QuestionItemRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return questionModelList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(questionModelList[position])
    }
}