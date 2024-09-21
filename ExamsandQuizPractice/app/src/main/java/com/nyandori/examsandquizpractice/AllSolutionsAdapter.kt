package com.nyandori.examsandquizpractice

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nyandori.examsandquizpractice.databinding.EachSolutionRecyclerBinding

class AllSolutionsAdapter(private val quizModelList: List<QuizModel>): RecyclerView.Adapter<AllSolutionsAdapter.MyViewHolder>() {
    class MyViewHolder(private val binding: EachSolutionRecyclerBinding): RecyclerView.ViewHolder(binding.root) {

      @SuppressLint("SetTextI18n")
      fun bind(model: QuizModel){
          binding.apply {

              //questionNumber.text = "${ model.questionNumber }. "
              question.text = model.question
              choiceA.text = model.choices.A
              choiceB.text = model.choices.B
              choiceC.text = model.choices.C
              choiceD.text = model.choices.D
              //solution.text = model.solution
              correctOption.text = "Correct Option: ${model.correctOption}"

          }
      }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AllSolutionsAdapter.MyViewHolder {
        val binding = EachSolutionRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllSolutionsAdapter.MyViewHolder, position: Int) {
        holder.bind(quizModelList[position])
    }

    override fun getItemCount(): Int {
        return quizModelList.size
    }
}