package com.nyandori.footballhighlights

import java.util.ArrayList


data class Cards(
    val HomeCards: List<HomeCards>,
    val AwayCards: List<AwayCards>
){
    constructor(): this(emptyList(), emptyList())
}

data class HomeCards(
    val   cardType: String,
    val   elapsed: String,
    val playerName: String,
    val  side: String
)
{
    constructor(): this("","", "","")
}
data class AwayCards(
    val   cardType: String,
    val   elapsed: String,
    val playerName: String,
    val  side: String
)
{
    constructor(): this("","", "","")
}


