package com.nyandori.youtubeplayer

import VideoModels
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nyandori.youtubeplayer.databinding.VideoItemRecyclerRowBinding

class VideoAdapter(private val videoModelList: List<VideoModels>): RecyclerView.Adapter<VideoAdapter.MyViewHolder>(){
    class MyViewHolder(private val binding: VideoItemRecyclerRowBinding): RecyclerView.ViewHolder (binding.root) {
        fun bind(models: VideoModels){
            binding.apply {


                binding.videoTitle.text = models.videoTitle


                root.setOnClickListener {
                    VideoActivity.videoId = models.videoId
                    VideoActivity.videoTitle = models.videoTitle

                    val intent = Intent(root.context, VideoActivity::class.java)
                    root.context.startActivity(intent)

                }
            }



        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = VideoItemRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return videoModelList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(videoModelList[position])
    }
}