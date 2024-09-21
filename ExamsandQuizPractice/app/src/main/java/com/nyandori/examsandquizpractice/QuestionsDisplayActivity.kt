package com.nyandori.examsandquizpractice

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import com.nyandori.examsandquizpractice.databinding.ActivityQuestionsDisplayBinding
import com.nyandori.examsandquizpractice.databinding.ScoreDialogBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.IOException
import kotlin.properties.Delegates
import kotlin.random.Random


class QuestionsDisplayActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityQuestionsDisplayBinding
    companion object{
        var link: String = ""
        var data: MutableList<QuizModel> = mutableListOf()

    }
    private var currentQuestionIndex = 0
    private var selectedAnswer = ""
    private var score = 0
    private lateinit var adapter: AllSolutionsAdapter
    private lateinit var quizModelList: MutableList<QuizModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionsDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)



            quizModelList = data
        //getData(link)



       binding.apply {
           btn0.setOnClickListener(this@QuestionsDisplayActivity)
           btn1.setOnClickListener(this@QuestionsDisplayActivity)
           btn2.setOnClickListener(this@QuestionsDisplayActivity)
           btn3.setOnClickListener(this@QuestionsDisplayActivity)
           nextBtn.setOnClickListener(this@QuestionsDisplayActivity)
       }
        loadQuestions()
    }

    @SuppressLint("SetTextI18n")
    private fun loadQuestions() {

        
        selectedAnswer = ""
        if (currentQuestionIndex== quizModelList.size){
            finishQuiz()

            return
        }
        binding.apply {
            questionIndicatorTextview.text = "Question ${currentQuestionIndex+1}/${quizModelList.size}"

            if (quizModelList[currentQuestionIndex].question.isEmpty()){
                questionTextview.text = quizModelList[currentQuestionIndex+1].question
                btn0.text = quizModelList[currentQuestionIndex+1].choices.A
                btn1.text = quizModelList[currentQuestionIndex+1].choices.B
                btn2.text = quizModelList[currentQuestionIndex+1].choices.C
                btn3.text = quizModelList[currentQuestionIndex+1].choices.D
            }
            else{

                questionTextview.text = quizModelList[currentQuestionIndex].question
                btn0.text = quizModelList[currentQuestionIndex].choices.A
                btn1.text = quizModelList[currentQuestionIndex].choices.B
                btn2.text = quizModelList[currentQuestionIndex].choices.C
                btn3.text = quizModelList[currentQuestionIndex].choices.D

            }



        }
    }

    private fun setRecyclerView(){
        binding.progressBar.visibility = View.GONE
        adapter = AllSolutionsAdapter(quizModelList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }
/*
    private fun getData(url:String){
        try {

            val listSection = listOf(1, 2, 3, 4, 5)
            val pageSection = listOf(1, 2, 3, 4, 6, 7, 8)

                for (i in 1..5) {
                    val listSectionElement = listSection[Random.nextInt(listSection.size)]
                    val pageSectionElement = pageSection[Random.nextInt(pageSection.size)]
                    val string = "?section=$listSectionElement&page=$pageSectionElement"
                    val urlTo = url + string
                    quizModelList.addAll(getDataPerPage(urlTo))

                }




                    // Update your UI with the fetched data (e.g., update a RecyclerView adapter)
                    print(quizModelList)

                    setRecyclerView()




        }

        catch(e:IOException) {
            e.printStackTrace()
        }
    }

    private fun getDataPerPage(url: String): MutableList<QuizModel>{
        val quizList: MutableList<QuizModel> = mutableListOf()

        try {
            // Fetch the HTML content
            val doc: Document =
                Jsoup.connect(url)
                    .get()

            // Select the articles containing questions
            val articles: Elements =
                doc.select("article.question.single-question.question-type-normal:not(.center)")
            val articlesWithoutLast =
                if (articles.size > 1) articles.subList(0, articles.size - 1) else articles
            val articleList = mutableListOf<Map<String, Any>>()

            Log.d("Number of Elements", "There are ${articlesWithoutLast.size} in the page ")
            for (article in articlesWithoutLast) {
                // Extract the question number
                val questionNumber: String =
                    article.select("h2 div.question-number").text().replace(".", "")

                // Extract the question
                val question: String = article.select("div.question-main").text()

                // Extract the options
                val options: Elements = article.select("div.form-inputs p")
                val choices = mutableMapOf<String, String>()
                for (option in options) {
                    val label: Element? = option.selectFirst("label")
                    if (label != null) {
                        val labelText: String = label.text().replace(".", "")
                        val choiceText: String =
                            option.select("label[for=${option.selectFirst("input")?.id()}]").text()
                        choices[labelText] = choiceText
                    }
                }
                val A = choices["A"].toString()
                val B = choices["B"].toString()
                val C = choices["C"].toString()
                val D = choices["D"].toString()

                val answers = Choices(A, B, C, D)

                // Extract the correct option
                val correctOptionElement: Element? = article.selectFirst("strong")
                val correctOption: String = correctOptionElement?.text()?.trim() ?: ""


                // Extract the solution and explanation
                val solutionContainer: Element? = article.selectFirst("div.answer_container")
                val solutionText: String = solutionContainer?.select("div")?.get(2)?.text() ?: ""

                val solutionDivs: Elements = article.select("span.color")
                val solutionDiv: Element? = if (solutionDivs.size == 2) {
                    solutionDivs[1].parent().parent()
                } else {
                    solutionDivs[0]
                }
                val detailedSolutionText: String = solutionDiv?.text()?.replace("\n", "\n") ?: ""
                println(detailedSolutionText)
                val row = mapOf(
                    "questionNumber" to questionNumber,
                    "question" to question,
                    "choices" to choices,
                    "correctOption" to correctOption,
                    "solution" to detailedSolutionText
                )
                val quizModel: QuizModel = QuizModel(
                    questionNumber,
                    question,
                    answers,
                    correctOption,
                    detailedSolutionText

                )
                quizList.add(quizModel)
                // Add the map to the list
                articleList.add(row)
            }
        }
        catch (e: IOException){
            e.printStackTrace()
        }



        return  quizList



    }

*/




    override fun onClick(view: View?) {
        val clickedBtn = view as Button
        binding.apply {
            btn0.setBackgroundColor(getColor(R.color.gray))
            btn1.setBackgroundColor(getColor(R.color.gray))
            btn2.setBackgroundColor(getColor(R.color.gray))
            btn3.setBackgroundColor(getColor(R.color.gray))

        }
        if (clickedBtn.id == R.id.next_btn){



            if (selectedAnswer.isEmpty()){
                Toast.makeText(this, "Please select answer to continue", Toast.LENGTH_SHORT).show()
                return
            }
            if (selectedAnswer== quizModelList[currentQuestionIndex].correctOption){
                score ++

            } else{
                Toast.makeText(this, "The correct answer is ${quizModelList[currentQuestionIndex].correctOption}", Toast.LENGTH_SHORT).show()

            }
            currentQuestionIndex ++
            loadQuestions()

        }
        else{
            if (clickedBtn.id == R.id.btn0){
                selectedAnswer = "Option A"
            }else if (clickedBtn.id==R.id.btn1){
                selectedAnswer = "Option B"
            }
            else if (clickedBtn.id==R.id.btn2){
                selectedAnswer = "Option C"
            }
            else if (clickedBtn.id==R.id.btn3){
                selectedAnswer = "Option D"
            }


            clickedBtn.setBackgroundColor(getColor(R.color.orange))
        }
    }
    private fun finishQuiz(){


        val totalQuestions = quizModelList.size
        val percentage = ((score.toFloat() / totalQuestions.toFloat())*100).toInt()
        val dialogBinding = ScoreDialogBinding.inflate(layoutInflater)
        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setCancelable(false)
            .create()
        dialogBinding.apply {
            scoreProgressIndicator.progress = percentage
            scoreProgressText.text = "$percentage %"
            if (percentage > 60){
                scoreTitle.text = "Congrats! You have passed"
                scoreTitle.setTextColor(getColor(R.color.blue))
            }else{
                scoreTitle.text = "Oops! You have failed"
                scoreTitle.setTextColor(getColor(R.color.orange))
            }
            scoreSubtitle.text = "$score out of $totalQuestions are correct"
            finishButton.setOnClickListener {
                finish()
            }

            showAll.setOnClickListener {
                binding.cardView.visibility = View.GONE
                setRecyclerView()
                binding.relativeLayout.visibility = View.VISIBLE

                alertDialog.dismiss()


            }
        }

        alertDialog.show()

    }


}