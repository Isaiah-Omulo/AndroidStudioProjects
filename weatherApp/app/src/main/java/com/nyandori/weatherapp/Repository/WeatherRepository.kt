package com.nyandori.weatherapp.Repository

import com.nyandori.weatherapp.server.ApiServices

class WeatherRepository(val api: ApiServices) {

    fun getCurrentWeather(lat: Double, lon:Double, unit:String)=
        api.getCurrentWeather(lat, lon, unit, "7c6a45e9088c04d22c45574340d5c2f7")
}