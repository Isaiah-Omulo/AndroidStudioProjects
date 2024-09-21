package com.nyandori.examsandquizpractice

import android.os.Bundle
import android.util.Log
import android.view.View
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
import com.google.firebase.database.FirebaseDatabase
import com.nyandori.examsandquizpractice.databinding.ActivityMainBinding
import com.nyandori.examsandquizpractice.ui.theme.ExamsAndQuizPracticeTheme

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var questionModelList: MutableList<QuestionModel>
    private lateinit var adapter: MainQuestionAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        questionModelList = mutableListOf()
        getData()
    }
    private fun setUpRecyclerView(){
        binding.progressBar.visibility = View.GONE
        adapter = MainQuestionAdapter(questionModelList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

    }
    private fun getData(){

        binding.progressBar.visibility = View.VISIBLE
        FirebaseDatabase.getInstance().reference
            .get().addOnSuccessListener { datasnapshot->
                if (datasnapshot.exists()){
                    for (snapshot in datasnapshot.children){
                        val dataList = snapshot.getValue(QuestionModel::class.java)
                        if (dataList != null) {
                            questionModelList.add(dataList)
                        }
                        Log.d("Data", "The data is: ${dataList.toString()}")
                    }
                }
                setUpRecyclerView()
            }

    }
}
