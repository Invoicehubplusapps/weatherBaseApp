package com.krupa.baseweatherapp.apiCall

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("weather")
    suspend fun getWeatherForCity(@Query("q") city: String, @Query("appid") apiKey: String): WeatherResponse
}