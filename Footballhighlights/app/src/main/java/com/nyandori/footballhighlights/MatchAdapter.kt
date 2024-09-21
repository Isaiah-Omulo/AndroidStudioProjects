package com.nyandori.footballhighlights

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nyandori.footballhighlights.databinding.MatchItemRecyclerRowBinding

class MatchAdapter (private  val matchModelList: List<MatchModel>):
    RecyclerView.Adapter<MatchAdapter.MyViewHolder> () {
    class MyViewHolder (private val binding: MatchItemRecyclerRowBinding):RecyclerView.ViewHolder (binding.root) {
        fun bind(model: MatchModel){
            binding.apply {
                league.text = model.league
                headerText.text = model.header
                time.text = model.time

                root.setOnClickListener {
                    MatchActivity.data = model.data

                    val intent = Intent(root.context, MatchActivity::class.java)
                    root.context.startActivity(intent)
                }



            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchAdapter.MyViewHolder {
        val binding = MatchItemRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatchAdapter.MyViewHolder, position: Int) {
        holder.bind(matchModelList[position])

    }

    override fun getItemCount(): Int {
   return matchModelList.size
    }
}