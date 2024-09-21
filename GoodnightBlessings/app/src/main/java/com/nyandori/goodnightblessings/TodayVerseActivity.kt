package com.nyandori.goodnightblessings

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nyandori.goodnightblessings.databinding.ActivityTodayVerseBinding

class TodayVerseActivity : AppCompatActivity() {

    companion object{
        var good_night_message: GoodNightMessage = GoodNightMessage()
    }

    private lateinit var binding: ActivityTodayVerseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodayVerseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bibleVerse.text = good_night_message.bible_verse
        binding.bibleVerseChapter.text = good_night_message.bible_verse_chapter



        binding.btnHome.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.btnBack.setOnClickListener {

            val intent = Intent(this, GoodNightMessageActivity::class.java)
            startActivity(intent)
        }
    }

}