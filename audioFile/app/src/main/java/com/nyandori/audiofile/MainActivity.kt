package com.nyandori.audiofile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nyandori.audiofile.databinding.ActivityMainBinding
import com.nyandori.audiofile.ui.theme.AudioFileTheme
import java.io.IOException

class MainActivity : ComponentActivity() {
    lateinit var binding: ActivityMainBinding
    private val PICK_AUDIO_REQUEST_CODE = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

       binding.uploadButton.setOnClickListener {
            openFilePicker()
        }

    }
    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "audio/*" // Only audio files
        startActivityForResult(intent, PICK_AUDIO_REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_AUDIO_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                // Handle the selected audio file URI here
                uploadAudioFile(uri)
            }
        }
    }
    private fun uploadAudioFile(uri: Uri) {
        try {
            // Open input stream for the selected audio file
            contentResolver.openInputStream(uri)?.use { inputStream ->
                // Create a FileOutputStream to write the file to internal storage
                val outputStream = openFileOutput("uploaded_audio.wav", Context.MODE_PRIVATE)

                // Copy the content of the input stream to the output stream
                inputStream.copyTo(outputStream)

                // Close the streams
                outputStream.close()
            }

            // Optionally, you can show a message to indicate successful upload
            Toast.makeText(this, "Audio file uploaded successfully", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            // Handle any errors that occur during file copying
            e.printStackTrace()
            // Optionally, you can show an error message
            Toast.makeText(this, "Failed to upload audio file", Toast.LENGTH_SHORT).show()
        }
    }

}
