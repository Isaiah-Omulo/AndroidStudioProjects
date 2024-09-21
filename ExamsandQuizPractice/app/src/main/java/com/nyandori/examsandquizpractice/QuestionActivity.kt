package com.nyandori.examsandquizpractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.nyandori.examsandquizpractice.databinding.ActivityQuestionBinding

class QuestionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuestionBinding
    companion object{
        var subList:  List<SubList> = emptyList()
    }
    private lateinit var adapter: SubQuestionAdapter
    private lateinit var questionsList: List<SubList>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)
         setRecyclerViewContent()

        binding.backBtn.setOnClickListener {
            val  intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }

    private fun setRecyclerViewContent(){
        questionsList = subList
        adapter = SubQuestionAdapter(questionsList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }
}