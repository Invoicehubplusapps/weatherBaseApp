package com.krupa.baseweatherapp

import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier.Companion.any
import com.krupa.baseweatherapp.apiCall.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherViewModelTest {
    private lateinit var viewModel: WeatherViewModel
    private lateinit var repository: WeatherRepository

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined) // Set the main coroutine dispatcher for testing

        repository = mockk()
        viewModel = WeatherViewModel(repository)
    }

    @Test
    fun shouldUpdateStateWithWeatherDataWhenCityIsProvided() {
        // Given
        val fakeWeatherResponse = createFakeWeatherResponse()
        coEvery { repository.getWeatherForCity(any()) } returns fakeWeatherResponse

        val observer = mockk<MutableState<WeatherResult>>(relaxed = true)
        viewModel.weatherState.observe(observer)

        // When
        viewModel.getWeatherForCity("TestCity")

        // Then
        // Verify that the state is updated with the expected result
        observer.value shouldBeEqualTo WeatherResult.Success(fakeWeatherResponse)
    }

    @Test
    fun shouldUpdateStateWithErrorWhenAPICallFails() {
        // Given
        val fakeErrorMessage = "An error occurred"
        coEvery { repository.getWeatherForCity(any()) } throws Exception(fakeErrorMessage)

        val observer = mockk<MutableState<WeatherResult>>(relaxed = true)
        viewModel.weatherState.observe(observer)

        // When
        viewModel.getWeatherForCity("TestCity")

        // Then
        // Verify that the state is updated with the expected error result
        observer.value shouldBeEqualTo WeatherResult.Error(fakeErrorMessage)
    }

    private fun createFakeWeatherResponse(): WeatherResponse {
        // Create a fake WeatherResponse for testing
        return WeatherResponse(
            Coord(0.0, 0.0),
            listOf(Weather(800, "Clear", "clear sky", "01d")),
            "stations",
            Main(20.0, 19.5, 18.0, 22.0, 1010, 50, 1010, 1000),
            10000,
            Wind(5.0, 180, 5.0),
            Clouds(0),
            1696552086,
            Sys(1, 123, "US", 1234567890, 1234567890),
            7200,
            1234567890,
            "TestCity",
            200
        )
    }
}