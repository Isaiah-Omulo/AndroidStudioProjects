package com.nyandori.footballhighlights

data class Goals(
    val  AwayGoals: List<AwayGoals>  ,
    val HomeGoals: List<HomeGoals>
){
    constructor(): this(emptyList(), emptyList())
}

data class HomeGoals(
    val elapsed : String,
    val playerName: String,
    val side: String
){
    constructor(): this("", "","")
}

data class AwayGoals(
    val elapsed : String,
    val playerName: String,
    val side: String
){
    constructor(): this("", "","")
}
