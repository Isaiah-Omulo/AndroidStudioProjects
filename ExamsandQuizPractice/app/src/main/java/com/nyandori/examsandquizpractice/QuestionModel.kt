package com.nyandori.examsandquizpractice

data class QuestionModel(
    val mainLink: String,
    val mainTitle: String,
    val subList: List<SubList>


){
    constructor(): this("","", emptyList())
}
data class SubList(
    val numberOfPages: String,
    val numberOfSections: String,
    val subLink: String,
    val subTitle: String,


){
    constructor(): this("", "", "", "")
}

