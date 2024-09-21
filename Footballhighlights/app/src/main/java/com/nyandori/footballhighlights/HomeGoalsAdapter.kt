package com.nyandori.footballhighlights

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nyandori.footballhighlights.databinding.HomeGoalItemRecyclerRowBinding

class HomeGoalsAdapter(private val homeGoalsList: List<HomeGoals>):
    RecyclerView.Adapter<HomeGoalsAdapter.MyViewHolder>()
{
    class MyViewHolder(private val binding: HomeGoalItemRecyclerRowBinding): RecyclerView.ViewHolder(binding.root)  {
         fun bind(goals: HomeGoals){
             binding.apply {
                 playerName.text = goals.playerName
                 elapsed.text = goals.elapsed.toString()
                 side.text  = goals.side
             }
         }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = HomeGoalItemRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return homeGoalsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(homeGoalsList[position])
    }
}