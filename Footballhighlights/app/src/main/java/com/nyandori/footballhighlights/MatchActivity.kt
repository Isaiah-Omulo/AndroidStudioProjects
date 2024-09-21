package com.nyandori.footballhighlights

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.nyandori.footballhighlights.databinding.ActivityMatchBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class MatchActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMatchBinding
    companion object{
        var data:data = data()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val youTubePlayerView = binding.youtubePlayerView
        lifecycle.addObserver(youTubePlayerView)
        val video: String = data.video_id
        binding.progressBar.visibility = View.VISIBLE

        // Use a Handler to remove the ProgressBar after 5 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            binding.progressBar.visibility = View.GONE
        }, 5000)
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {

               youTubePlayer.loadVideo(video, 0f, )
            }
        })



        binding.scores.text =  "${data.dataFromComponents.homeScore} : ${data.dataFromComponents.awayScore}"
        binding.awayTeam.text = data.dataFromComponents.away_team_name
        binding.homeTeam.text = data.dataFromComponents.home_team_name
        Log.d("Cards", "Card Info: ${MatchDetailsActivity.dataFromComponents.Cards}")

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnNext.setOnClickListener {
            MatchDetailsActivity.awayTeam = data.dataFromComponents.away_team_name
            MatchDetailsActivity.homeTeam = data.dataFromComponents.home_team_name
            MatchDetailsActivity.dataFromComponents = data.dataFromComponents
            val intent = Intent(this, MatchDetailsActivity::class.java)
            startActivity(intent)

        }

    }



}