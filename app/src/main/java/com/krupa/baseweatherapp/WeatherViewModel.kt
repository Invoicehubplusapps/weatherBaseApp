package com.krupa.baseweatherapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krupa.baseweatherapp.apiCall.WeatherRepository
import com.krupa.baseweatherapp.apiCall.WeatherResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel (private val repository: WeatherRepository) : ViewModel() {

    private val _weatherState = mutableStateOf<WeatherResult>(WeatherResult.Loading)
    val weatherState: State<WeatherResult> = _weatherState

    fun getWeatherForCity(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val weather = repository.getWeatherForCity(city)
                _weatherState.value = WeatherResult.Success(weather)
            } catch (e: Exception) {
                _weatherState.value = WeatherResult.Error(e.message ?: "An error occurred")
            }
        }
    }

}