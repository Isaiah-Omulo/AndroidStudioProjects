package com.nyandori.firebasestorage

import retrofit2.Call
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/predict")
    fun predict(@Body inputData: InputData): Call<ResponseBody>
}