package com.nyandori.bettingapp


data class BetCategoriesModel(
    val betList: List<BetModel>,
    val id: String,
    val title: String,
    val subtitle:String,

){
    constructor():this(emptyList(),"","", "")
}


data class BetModel(
    val League : String,
    val Match: String,
    val Score: String,
    val Time: String,
    val Tip: String,
    val id: Long,

){
constructor():this("", "","","","",0)
}
