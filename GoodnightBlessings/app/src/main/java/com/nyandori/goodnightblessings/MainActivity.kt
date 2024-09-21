package com.nyandori.goodnightblessings

import android.os.Bundle
import android.util.Log
import android.view.View
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.nyandori.goodnightblessings.databinding.ActivityMainBinding
import com.nyandori.goodnightblessings.ui.theme.GoodnightBlessingsTheme

class MainActivity : ComponentActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var messageAdapter: MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getMessages()
    }

    fun getMessages(){
        binding.progressBar.visibility = View.VISIBLE
        FirebaseFirestore.getInstance().collection("goodnight_messages")
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .get().addOnSuccessListener {


                val messageList = it.toObjects(GoodNightMessageModel::class.java)
                setupDevotionRecyclerView(messageList)
            }.addOnFailureListener{
                Toast.makeText(this, "It has failed", Toast.LENGTH_LONG).show()
                Log.d("Error", it.toString())

            }
    }

    fun setupDevotionRecyclerView(messageList: List<GoodNightMessageModel>){
        binding.progressBar.visibility = View.GONE
        messageAdapter = MyAdapter(messageList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false )

        binding.recyclerView.adapter = messageAdapter


    }
}
