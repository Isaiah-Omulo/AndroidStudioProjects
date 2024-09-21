package com.nyandori.weatherforecastingapp.data.pollutionModels


import com.google.gson.annotations.SerializedName

data class PollutionData(
    @SerializedName("coord")
    val coord: Coord,
    @SerializedName("list")
    val list: List<Pollution>
)