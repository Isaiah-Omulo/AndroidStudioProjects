package com.nyandori.footballhighlights

data class data(
    val dataFromComponents: dataFromComponents,
    val video_id: String
){
    constructor(): this (dataFromComponents(), "")
}
