package com.nyandori.bettingapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.nyandori.bettingapp.databinding.BetActivityRecyclerViewerBinding

class BetActivityAdapter(private val betModelList: List<BetModel>): RecyclerView.Adapter<BetActivityAdapter.ViewHolder>() {
    class ViewHolder(private val binding: BetActivityRecyclerViewerBinding ):RecyclerView.ViewHolder(binding.root) {
     fun bind(model: BetModel){
           binding.apply {
               league.text = model.League
               match.text = model.Match
               time.text = model.Time
              tips.text = model.Tip
           }


     }


    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BetActivityAdapter.ViewHolder {

        val binding = BetActivityRecyclerViewerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(betModelList[position])
    }

    override fun getItemCount(): Int {
        return betModelList.size
    }


}