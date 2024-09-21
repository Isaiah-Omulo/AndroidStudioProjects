package com.nyandori.weatherforecastingapp.data

import com.nyandori.weatherforecastingapp.data.forecastModels.forecast
import com.nyandori.weatherforecastingapp.data.models.CurrentWeather
import com.nyandori.weatherforecastingapp.data.pollutionModels.PollutionData
import retrofit2.Response
import retrofit2.http.Query

import retrofit2.http.GET
interface ApiInterface {
    @GET("weather?")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("units") units: String,
        @Query("appid") ApiKey: String,

    ):Response<CurrentWeather>

    @GET("forecast?")
    suspend fun getForecast(
        @Query("q") city: String,
        @Query("units") units: String,
        @Query("appid") ApiKey: String,

    ):Response<forecast>

    @GET("air_pollution?")
    suspend fun getPollution(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("appid") ApiKey: String,
    ): Response<PollutionData>
}