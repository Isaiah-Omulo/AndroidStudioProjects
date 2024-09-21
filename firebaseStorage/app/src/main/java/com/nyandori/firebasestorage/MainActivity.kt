package com.nyandori.firebasestorage

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.nyandori.firebasestorage.databinding.ActivityMainBinding
import com.nyandori.firebasestorage.ui.theme.FirebaseStorageTheme
import okhttp3.ResponseBody
import retrofit2.Call

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    private val PICK_AUDIO_REQUEST = 1
    private var path:String = ""
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


            binding.uploadButton.setOnClickListener {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "audio/*"
                startActivityForResult(intent, PICK_AUDIO_REQUEST)
            }


    }
    @SuppressLint("SetTextI18n")
    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_AUDIO_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val audioUri: Uri = data.data!!

            // Get a reference to the Firebase Storage instance
            val storage = Firebase.storage
            val storageRef = storage.reference

            // Create a reference to the location you want to upload the audio file
            val audioRef = storageRef.child("audio/${audioUri.lastPathSegment}")

            // Upload audio file to Firebase Storage
            val uploadTask = audioRef.putFile(audioUri)
            uploadTask.addOnSuccessListener { taskSnapshot ->
                // Audio file uploaded successfully
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                   path = uri.toString()
                    Log.d("Uploaded", "Download URL: $path" )

                }
            }
                .addOnFailureListener { exception ->
                    // Handle unsuccessful uploads

                    Log.d("Uploaded", "Upload Failed: ${exception.message}" )
                }
        }
    }


    private
    fun main() {
        // Define the input data
        val inputData = InputData("Hello")

        // Define the base URL of the FastAPI endpoint
        val baseUrl = "https://gradio-client.onrender.com"

        // Create a Retrofit instance
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create an instance of the ApiService interface
        val apiService = retrofit.create(ApiService::class.java)

        // Send a POST request to the endpoint with the input data
        val call = apiService.predict(inputData)

        // Execute the request asynchronously
        call.enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onResponse(call: Call<Map<String, String>>, response: retrofit2.Response<Map<String, String>>) {
                // Check if the request was successful (status code 200)
                if (response.isSuccessful) {
                    // Print the prediction result
                    println(response.body()?.get("prediction_result"))
                } else {
                    // Print an error message if the request failed
                    println("Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<Map<String, String>>, t: Throwable) {
                // Print an error message if the request failed
                println("Error: ${t.message}")
            }
        })
    }
}