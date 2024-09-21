package com.nyandori.youtubeplayer

import VideoModels
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.FirebaseDatabase
import com.nyandori.youtubeplayer.databinding.ActivityMainBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener


class MainActivity : ComponentActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var adapter:VideoAdapter
    lateinit var videoModelList: MutableList<VideoModels>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        videoModelList = mutableListOf()

        getDataFromDatabase()

    }

    private fun setUpRecyclerView(){
        binding.progressBar.visibility = View.GONE
        adapter = VideoAdapter(videoModelList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

    }
    private fun getDataFromDatabase(){
        binding.progressBar.visibility = View.VISIBLE
        FirebaseDatabase.getInstance().reference
            .get().addOnSuccessListener { dataSnapshot->
                if (dataSnapshot.exists()){
                    for (snapshot in dataSnapshot.children){
                        val videoModel = snapshot.getValue(VideoModels::class.java)

                        if (videoModel != null) {
                            videoModelList.add(videoModel)
                        }
                    }
                }
                setUpRecyclerView()
            }.addOnFailureListener { e->
                Toast.makeText(this, "It has failed", Toast.LENGTH_LONG).show()
                Log.d("Error", e.message.toString())
            }
    }
}

