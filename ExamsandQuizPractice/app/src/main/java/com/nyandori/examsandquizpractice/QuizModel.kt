package com.nyandori.examsandquizpractice

data class QuizModel(

    val questionNumber: String,
    val question: String,
    val choices: Choices,
    val correctOption: String,
    val solution: String
){
    constructor(): this("", "", Choices(), "","")
}



data class Choices(
    val A: String,
    val B: String,
    val C: String,
    val D:String
){
    constructor(): this("", "", "", "")

}
