package com.nyandori.goodnightblessings

import android.content.ContentValues.TAG
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.SeekBar
import androidx.core.net.toUri
import com.nyandori.goodnightblessings.databinding.ActivityGoodNightMessageBinding

class GoodNightMessageActivity : AppCompatActivity() {
    lateinit var binding: ActivityGoodNightMessageBinding

    companion object {
        var good_night_message: GoodNightMessage = GoodNightMessage()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoodNightMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.voicePlayerView.setAudio(good_night_message.url)
        binding.today.setOnClickListener {
            TodayVerseActivity.good_night_message = good_night_message

            binding.voicePlayerView.onStop()

            val intent = Intent(this, TodayVerseActivity::class.java)
            startActivity(intent)
        }
        binding.home.setOnClickListener {
            binding.voicePlayerView.onStop()


            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.stop.setOnClickListener {
            binding.voicePlayerView.onStop()


            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        //binding.Readings.text = dailyDevotion.description


        binding.voicePlayerView.imgPlay.setOnClickListener (
            binding.voicePlayerView.imgPlayClickListener)





        binding.voicePlayerView.imgPause.setOnClickListener {
            binding.voicePlayerView.onPause()
        }


    }
}