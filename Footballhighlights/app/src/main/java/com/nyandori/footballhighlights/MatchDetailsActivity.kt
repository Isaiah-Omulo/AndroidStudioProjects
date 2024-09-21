package com.nyandori.footballhighlights

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.nyandori.footballhighlights.databinding.ActivityMatchDetailsBinding

class MatchDetailsActivity : AppCompatActivity() {
   private lateinit var binding: ActivityMatchDetailsBinding
   private lateinit var awayGoalsAdapter: AwayGoalsAdapter
   private lateinit var awayCardsAdapter: AwayCardsAdapter
   private lateinit var homeGoalsAdapter: HomeGoalsAdapter
   private lateinit var homeCardsAdapter: HomeCardsAdapter

   private lateinit var awayGoalsList: MutableList<AwayGoals>
   private lateinit var awayCardsList: MutableList<AwayCards>
   private lateinit var homeGoalsList:MutableList<HomeGoals>
   private lateinit var homeCardsList: MutableList<HomeCards>
   companion object{
       var homeTeam: String = ""
       var awayTeam: String  = ""
       var dataFromComponents: dataFromComponents = dataFromComponents()

   }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.homeTeamTitle.text = homeTeam
        binding.awayTeamTitle.text = awayTeam
        awayCardsList = mutableListOf()
        homeCardsList = mutableListOf()
        awayGoalsList = mutableListOf()
        homeGoalsList = mutableListOf()

   setAdapters()
    }

    private fun setAdapters(){
        Log.d("Cards", "Card Info: ${dataFromComponents.Cards}")
        awayCardsList = dataFromComponents.Cards.AwayCards.toMutableList()
        homeCardsList = dataFromComponents.Cards.HomeCards.toMutableList()

        awayCardsAdapter = AwayCardsAdapter(awayCardsList)
        awayGoalsAdapter = AwayGoalsAdapter(awayGoalsList)
        homeCardsAdapter = HomeCardsAdapter(homeCardsList)
        homeGoalsAdapter = HomeGoalsAdapter(homeGoalsList)

        binding.awayTeamCards.layoutManager = LinearLayoutManager(this)
        binding.awayTeamCards.adapter = awayCardsAdapter

        binding.homeTeamCards.layoutManager = LinearLayoutManager(this)
        binding.homeTeamCards.adapter = homeCardsAdapter

    }


}