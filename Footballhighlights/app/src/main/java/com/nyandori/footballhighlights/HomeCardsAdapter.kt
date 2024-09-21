package com.nyandori.footballhighlights

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nyandori.footballhighlights.databinding.HomeCardsItemRecyclerRowBinding

class HomeCardsAdapter(private val homeCardsList: List<HomeCards>):RecyclerView.Adapter<HomeCardsAdapter.MyViewHolder> () {
    class MyViewHolder (private val binding: HomeCardsItemRecyclerRowBinding): RecyclerView.ViewHolder(binding.root) {
     fun bind(cards: HomeCards){
         binding.apply {
             playerName.text = "${cards.playerName} (${cards.elapsed}) "

             if (cards.cardType=="r"){
                 cardImage.setImageDrawable(ContextCompat.getDrawable(root.context, R.drawable.red_card))

             }
         }
     }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = HomeCardsItemRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return homeCardsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(homeCardsList[position])
    }
}