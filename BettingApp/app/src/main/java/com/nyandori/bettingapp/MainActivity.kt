package com.nyandori.bettingapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nyandori.bettingapp.databinding.ActivityMainBinding
import com.google.firebase.database.FirebaseDatabase


class MainActivity : ComponentActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var betModelList: MutableList<BetCategoriesModel>
    lateinit var adapter: BetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        betModelList = mutableListOf()

        getDataFromFirebase()




    }
    private fun setUpRecyclerView(){
        binding.progressBar.visibility = View.GONE
        adapter = BetAdapter(betModelList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

    }

    private fun  getDataFromFirebase(){

        binding.progressBar.visibility = View.VISIBLE
        FirebaseDatabase.getInstance().reference
            .get().addOnSuccessListener {dataSnapshot->
                if (dataSnapshot.exists()){

                    for (snapshot in dataSnapshot.children){

                        val bets = snapshot.getValue(BetCategoriesModel::class.java)

                        if (bets != null) {
                            betModelList.add(bets)
                        }


                    }


                }
                setUpRecyclerView()

            }.addOnFailureListener{
                Toast.makeText(this, "It has failed", Toast.LENGTH_LONG).show()

            }




        /*
                var listQuestionModel = mutableListOf<QuestionModel>()
                listQuestionModel.add(QuestionModel("What is android OS?", mutableListOf("Language", "OS","Product", "None"), "OS"))
                listQuestionModel.add(QuestionModel("Who owns android?", mutableListOf("Apple", "Google","Samsung", "Microsoft"), "Google"))

                listQuestionModel.add(QuestionModel("Which assistant android uses?", mutableListOf("Siri", "Cortana","Google Assistant", "Alexa"), "Google Assistant"))
                QuizModelList.add(QuizModel(id="1", title="Programming",subtitle="All the basic programming", time="10",listQuestionModel))
        //QuizModelList.add(QuizModel(id="2", title="Computer",subtitle="All the computer questions", time="20"))
        //QuizModelList.add(QuizModel(id="3", title="Geography",subtitle="Boost your Geography knowledge", time="15"))
               */
        setUpRecyclerView()

    }
}