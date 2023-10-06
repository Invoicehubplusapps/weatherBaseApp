package com.krupa.baseweatherapp

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.krupa.baseweatherapp.apiCall.WeatherResponse
import com.krupa.baseweatherapp.apiCall.WeatherResult

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WeatherScreen(viewModel: WeatherViewModel = viewModel()) {
    var city by remember { mutableStateOf("") }
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val weatherState by rememberUpdatedState(newValue = viewModel.weatherState.value)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("Enter city") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                viewModel.getWeatherForCity(city)
                keyboardController?.hide()
            }),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))


        when (weatherState) {
            is WeatherResult.Loading -> {
                CircularProgressIndicator(modifier = Modifier.wrapContentSize(Alignment.Center))
            }
            is WeatherResult.Success -> {
                val weatherResponse: WeatherResponse = (weatherState as WeatherResult.Success).weather
                Log.e("result",weatherResponse.base)
                // Display weather information in the UI
                // Example: Text("Temperature: ${result.weather.temperature}°C\nDescription: ${result.weather.description}")
            }
            is WeatherResult.Error -> {
                // Handle error, e.g., show a snackbar
                // SnackbarHost(hostState = remember { SnackbarHostState() }) {
                //    Snackbar(message = { Text(result.message) })
                //}
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewWeatherScreen() {
    WeatherScreen()
}
