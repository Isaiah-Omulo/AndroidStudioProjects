data class VideoModels(
    val id:String,
    val videoId: String,
    val videoTitle: String
){
    constructor(): this("", "","")
}