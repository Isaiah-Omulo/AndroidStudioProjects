package com.nyandori.weatherapp.viewModel

import androidx.lifecycle.ViewModel
import com.nyandori.weatherapp.Repository.WeatherRepository
import com.nyandori.weatherapp.server.ApiClient
import com.nyandori.weatherapp.server.ApiServices

class WeatherViewModel(val repository: WeatherRepository): ViewModel() {

    constructor():this(WeatherRepository(ApiClient().getClient().
    create(ApiServices::class.java)))

    fun loadCurrentWeather(lat:Double, lon:Double, unit:String)=
        repository.getCurrentWeather(lat,lon,unit)

}