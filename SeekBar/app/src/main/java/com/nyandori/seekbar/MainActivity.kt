package com.nyandori.seekbar

import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.nyandori.seekbar.databinding.ActivityMainBinding
import com.nyandori.seekbar.ui.theme.SeekBarTheme

class MainActivity : ComponentActivity(), Runnable {

    lateinit var binding: ActivityMainBinding
    private var mediaPlayer: MediaPlayer? = MediaPlayer()

    private var wasPlaying = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            val  song = "https://firebasestorage.googleapis.com/v0/b/daily-devotion-72b13.appspot.com/o/devotion_preaching%2F13TH%20APRIL%202024_daily_devotion.mp4?alt=media&token=108722cf-3c27-41ed-966d-f0c60044d2fd"
            MediaPlayer.create(this, song.toUri() )

        }
        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar) {
                binding.seekBarHint.visibility = View.VISIBLE
            }

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromTouch: Boolean) {
                binding.seekBarHint.visibility = View.VISIBLE
                val x = Math.ceil((progress / 1000f).toDouble()).toInt()

                if (x == 0 && mediaPlayer != null && !mediaPlayer!!.isPlaying) {
                    clearMediaPlayer()
                    binding.fab.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, android.R.drawable.ic_media_play))
                    binding.seekbar.progress = 0
                }
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
                    mediaPlayer!!.seekTo(seekBar.progress)
                }
            }
        })

    }
    fun playSong() {
        try {
            if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
                clearMediaPlayer()
                binding.seekbar.progress = 0
                wasPlaying = true
                binding.fab.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, android.R.drawable.ic_media_play))
            }

            if (!wasPlaying) {
                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer()

                }

                binding.fab.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, android.R.drawable.ic_media_pause))
                val descriptor: AssetFileDescriptor = assets.openFd("suits.mp3")
                mediaPlayer?.setDataSource(descriptor.fileDescriptor, descriptor.startOffset, descriptor.length)
                descriptor.close()

                mediaPlayer?.setVolume(0.5f, 0.5f)
                mediaPlayer?.isLooping = false
                binding.seekbar.max = mediaPlayer?.duration ?: 0

                mediaPlayer?.start()
                Thread(this).start()
                Log.d("Tag", "This is set: ${mediaPlayer?.currentPosition}")
            }

            wasPlaying = false
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        clearMediaPlayer()
    }

    private fun clearMediaPlayer() {
        mediaPlayer?.stop()
        mediaPlayer?.reset()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun run() {
        var currentPosition = mediaPlayer?.currentPosition ?: 0
        val total = mediaPlayer?.duration ?: 0

        while (mediaPlayer != null && mediaPlayer!!.isPlaying && currentPosition < total) {
            try {
                Thread.sleep(1000)
                currentPosition = mediaPlayer?.currentPosition ?: 0
            } catch (e: InterruptedException) {
                return
            } catch (e: Exception) {
                return
            }

            binding.seekbar.progress = currentPosition
        }
    }
}



