package com.nyandori.bettingapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.nyandori.bettingapp.databinding.BetItemRecyclerRowBinding

class BetAdapter(private val betModelList: MutableList<BetCategoriesModel>): RecyclerView.Adapter<BetAdapter.MyViewHolder>() {

    class MyViewHolder(private val binding: BetItemRecyclerRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(model: BetCategoriesModel){
            binding.apply {

                quizTitleText.text = model.title
                quizSubtitleText.text = model.subtitle
                //quizTimeText.text = model.Time


                root.setOnClickListener{

                    val intent = Intent(root.context, betActivity::class.java)
                    betActivity.betModelList = model.betList
                    //QuizActivity.questionModelList = model.questionList
                    //QuizActivity.time = model.time

                    root.context.startActivity(intent)
                }



            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BetAdapter.MyViewHolder {
        val binding = BetItemRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BetAdapter.MyViewHolder, position: Int) {
        holder.bind(betModelList[position])
    }

    override fun getItemCount(): Int {


        return betModelList.size
    }
}