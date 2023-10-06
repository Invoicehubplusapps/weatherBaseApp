package com.krupa.baseweatherapp.apiCall

class WeatherRepository(private val apiService: WeatherApiService) {

    suspend fun getWeatherForCity(city: String): WeatherResponse {
        return apiService.getWeatherForCity(city, "98d4202eefa0f1ec74113cc90b452ed8")
    }
}