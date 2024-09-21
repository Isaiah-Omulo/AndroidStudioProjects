package com.nyandori.footballhighlights

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nyandori.footballhighlights.databinding.AwayGoalItemRecyclerRowBinding

class AwayGoalsAdapter(private val awayGoalsList: List<AwayGoals>): RecyclerView.Adapter<AwayGoalsAdapter.MyViewHolder>() {
    class MyViewHolder(private val binding: AwayGoalItemRecyclerRowBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(goals: AwayGoals){
            binding.apply {
                playerName.text = goals.playerName
                elapsed.text = goals.elapsed.toString()
                side.text  = goals.side
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = AwayGoalItemRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return awayGoalsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(awayGoalsList[position])
    }
}