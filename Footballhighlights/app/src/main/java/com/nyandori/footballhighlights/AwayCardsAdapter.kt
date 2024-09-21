package com.nyandori.footballhighlights

import android.annotation.SuppressLint

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.PendingIntentCompat.getActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.nyandori.footballhighlights.databinding.AwayCardsItemRecyclerRowBinding

class AwayCardsAdapter (private val awayCardsList: List<AwayCards>): RecyclerView.Adapter<AwayCardsAdapter.MyViewHolder>() {
    class MyViewHolder(private val binding: AwayCardsItemRecyclerRowBinding):RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(cards: AwayCards){
            binding.apply {
                playerName.text = "${cards.playerName} (${cards.elapsed}) "


                if (cards.cardType=="r"){
                 cardImage.setImageDrawable(ContextCompat.getDrawable(root.context, R.drawable.red_card))

                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = AwayCardsItemRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return awayCardsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(awayCardsList[position])
    }
}