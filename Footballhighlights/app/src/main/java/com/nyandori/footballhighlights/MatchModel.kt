package com.nyandori.footballhighlights

data class MatchModel(
    val data: data,
    val header: String,
    val href:String,
    val league: String,
    val time: String
){
    constructor(): this(data(), "",
    "",
        "", "",
    )
}
