package com.nyandori.footballhighlights

data class dataFromComponents(
    val Cards: Cards,
    val Goals: Goals,
    val awayScore: String,
    val awayScoreFT: String,
    val awayScoreHT: String,
    val away_team_name: String,
    val homeScore:String,
    val  homeScoreFT: String,
    val homeScoreHT: String,
    val home_team_name: String,
    val match_id: String,
    val refereeName: String,
    val spectators: String,
    val status: String,
    val venueName: String,

){
    constructor(): this( Cards(), Goals(),"", "", "", "", ""
    , "", "","","","",
        "", "", ""
    )
}
