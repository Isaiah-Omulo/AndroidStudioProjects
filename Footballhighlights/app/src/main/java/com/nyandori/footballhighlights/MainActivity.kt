package com.nyandori.footballhighlights

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.nyandori.footballhighlights.databinding.ActivityMainBinding


class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var matchModelList: MutableList<MatchModel>
    lateinit var adapter: MatchAdapter
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        matchModelList = mutableListOf()

        getDataFromFirebase()
    }
    private fun setUpRecyclerView(){
        binding.progressBar.visibility = View.GONE
        adapter = MatchAdapter(matchModelList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

    }

    private fun getDataFromFirebase() {
        binding.progressBar.visibility = View.VISIBLE
        FirebaseDatabase.getInstance().reference
            .get()
            .addOnSuccessListener {dataSnapshot->
                if(dataSnapshot.exists()){
                    for ( snapshot in dataSnapshot.children){
                          val matchModel = snapshot.getValue(MatchModel::class.java)
                        Log.d("Match", "Match Info: $matchModel")

                        if (matchModel != null){
                               matchModelList.add(matchModel)
                           }
                    }
                }
                setUpRecyclerView()

            }.addOnFailureListener{
                Toast.makeText(this, "It has failed", Toast.LENGTH_LONG).show()

            }
    }
}
