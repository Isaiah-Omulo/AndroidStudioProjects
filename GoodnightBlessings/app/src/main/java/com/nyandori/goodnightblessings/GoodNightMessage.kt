package com.nyandori.goodnightblessings

data class GoodNightMessage(
    val bible_verse: String,
    val bible_verse_chapter: String,
    val title: String,
    val url: String
){
    constructor():this("","","","")
}