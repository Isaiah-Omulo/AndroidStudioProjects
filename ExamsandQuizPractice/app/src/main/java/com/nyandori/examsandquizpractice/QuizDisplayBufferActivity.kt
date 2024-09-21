package com.nyandori.examsandquizpractice

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.nyandori.examsandquizpractice.databinding.ActivityQuizDisplayBufferBinding
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.IOException
import kotlin.random.Random

class QuizDisplayBufferActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityQuizDisplayBufferBinding

    lateinit var quizModelList: MutableList<QuizModel>
    companion object{
        var link: String = ""
        var numberOfSections = 0
        var numberOfPages = 0

    }
    private var limit = 0
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizDisplayBufferBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        quizModelList = mutableListOf()
        binding.apply {

            if (numberOfSections == 0 ){
                if(numberOfPages==2){
                    btn0.text = "10"
                    btn1.text = "20"
                    btn2.visibility = View.GONE
                    btn3.visibility = View.GONE
                    btn4.visibility = View.GONE
                }
               else if(numberOfPages==3){
                    btn0.text = "10"
                    btn1.text = "20"
                    btn2.visibility = View.GONE
                    btn3.visibility = View.GONE
                    btn4.visibility = View.GONE
                }
                else if(numberOfPages==4){
                    btn0.text = "10"
                    btn1.text = "20"
                    btn2.visibility = View.GONE
                    btn3.visibility = View.GONE
                    btn4.visibility = View.GONE
                }
                else if(numberOfPages==5){
                    btn0.text = "10"
                    btn1.text = "20"
                    btn2.text = "50"
                    btn2.visibility = View.GONE
                    btn3.visibility = View.GONE
                    btn4.visibility = View.GONE
                    }

                else if(numberOfPages==6){
                    btn0.text = "10"
                    btn1.text = "20"
                    btn2.text = "50"
                    btn3.visibility= View.GONE
                    btn4.visibility = View.GONE
                }

                else if(numberOfPages==7){
                    btn0.text = "10"
                    btn1.text = "20"
                    btn2.text = "50"
                    btn3.visibility= View.GONE
                    btn4.visibility = View.GONE
                }
                else if(numberOfPages==8){
                    btn0.text = "10"
                    btn1.text = "20"
                    btn2.text = "50"
                    btn3.visibility= View.GONE
                    btn4.visibility = View.GONE
                }
                else if(numberOfPages==9){
                    btn0.text = "10"
                    btn1.text = "20"
                    btn2.text = "50"
                    btn3.visibility= View.GONE
                    btn4.visibility = View.GONE
                }

                else if(numberOfPages==10){
                    btn0.text = "10"
                    btn1.text = "20"
                    btn2.text = "50"
                    btn3.text= "100"
                    btn4.visibility = View.GONE
                }
            }
            else{
                val product = numberOfSections * 10 * numberOfPages
                if (product in 10..20){
                    btn0.text = "10"
                    btn1.text = "20"
                    btn2.visibility = View.GONE
                    btn3.visibility = View.GONE
                    btn4.visibility = View.GONE

                }
                else if (product in 20..50){
                    btn0.text = "10"
                    btn1.text = "20"
                    btn2.text = "50"
                    btn3.visibility = View.GONE
                    btn4.visibility = View.GONE

                }
                else if (product in 50..100){
                    btn0.text = "10"
                    btn1.text = "20"
                    btn2.text = "50"
                    btn3.text = "100"
                    btn4.visibility = View.GONE

                }
                else if (product > 100){
                    btn0.text = "10"
                    btn1.text = "20"
                    btn2.text = "50"
                    btn3.text = "100"
                    btn4.text ="200"

                }


            }


            btn0.setOnClickListener(this@QuizDisplayBufferActivity)
            btn1.setOnClickListener(this@QuizDisplayBufferActivity)
            btn2.setOnClickListener(this@QuizDisplayBufferActivity)
            btn3.setOnClickListener(this@QuizDisplayBufferActivity)
            btn4.setOnClickListener(this@QuizDisplayBufferActivity)
            nextBtn.setOnClickListener(this@QuizDisplayBufferActivity)
            backBtn.setOnClickListener(this@QuizDisplayBufferActivity)


        }
    }

    override fun onClick(view: View?) {
        val clickedBtn = view as Button
        binding.apply {
            btn0.setBackgroundColor(getColor(R.color.gray))
            btn1.setBackgroundColor(getColor(R.color.gray))
            btn2.setBackgroundColor(getColor(R.color.gray))
            btn3.setBackgroundColor(getColor(R.color.gray))
            btn4.setBackgroundColor(getColor(R.color.gray))

        }
        if (clickedBtn.id == R.id.next_btn ){
            if (limit==0){
                Toast.makeText(this, "Please select one to continue", Toast.LENGTH_SHORT).show()
                return
            }
            else{
                Toast.makeText(this, "Questions are loading", Toast.LENGTH_SHORT).show()

                binding.cardView.visibility = View.GONE
                binding.linearLayout.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE

                val data = getData(link, limit)

                QuestionsDisplayActivity.data=data
                binding.progressBar.visibility = View.GONE
                binding.cardView.visibility = View.VISIBLE
                binding.linearLayout.visibility = View.VISIBLE
                val intent = Intent(this, QuestionsDisplayActivity::class.java)
                startActivity(intent)





            }
        }
        else if (clickedBtn.id == R.id.back_btn){
            val intent = Intent(this, QuestionActivity::class.java)
            startActivity(intent)
        }
        else{
            if (clickedBtn.id == R.id.btn0){
                limit = 1
            }
            else if (clickedBtn.id == R.id.btn1){
                limit = 2
            }

            else if (clickedBtn.id == R.id.btn2){
                limit = 5
            }
            else if (clickedBtn.id == R.id.btn3){
                limit = 10
            }
            else if (clickedBtn.id == R.id.btn4){
                limit = 20
            }
            clickedBtn.setBackgroundColor(getColor(R.color.orange))


        }
    }


    private fun getData(url:String, lim: Int): MutableList<QuizModel> {
        try {

            if (numberOfSections==0){

                val listof = mutableListOf<Int>()
                for (i in 1..numberOfPages){
                    listof.add(i)
                }


                for (i in 0..<lim){
                    val string = "?page=${listof[i]}"
                    val urlTo = url + string
                    quizModelList.addAll(getDataPerPage(urlTo))
                }

            }

            else{
                val combinations = mutableListOf<Pair<Int, Int>>()

                for (section in 1.. numberOfSections) {
                    for (page in 1..numberOfPages) {
                        combinations.add(Pair(section, page))
                    }
                }
                print(combinations)
                combinations.shuffle(Random)
                quizModelList.clear()

                for (i in 0..lim) {
                    val string = "?section=${combinations[i].first}&page=${combinations[i].second}"
                    val urlTo = url + string
                    quizModelList.addAll(getDataPerPage(urlTo))

                }

            }

            // Update your UI with the fetched data (e.g., update a RecyclerView adapter)
            print(quizModelList)






        }

        catch(e: IOException) {
            e.printStackTrace()
        }
        return quizModelList
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

                val solutionDiv: Element? = when {
                    solutionDivs.size == 2 -> solutionDivs[1].parent()?.parent()
                    solutionDivs.isNotEmpty() -> solutionDivs[0]
                    else -> null
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
}