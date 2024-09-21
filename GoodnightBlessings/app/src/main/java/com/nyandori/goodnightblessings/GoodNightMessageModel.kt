package com.nyandori.goodnightblessings

data class GoodNightMessageModel(
    val date: String,
    val good_night_message: GoodNightMessage
){
    constructor(): this("",GoodNightMessage())
}