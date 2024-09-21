package com.nyandori.weatherforecastingapp.data.models


import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("1h")
    val h: Double
)