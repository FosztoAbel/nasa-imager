package main

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import models.NasaApiResponse
import models.ServerError

class MainScreenModel : StateScreenModel<MainScreenModel.State>(State.Init) {
    private val httpClient = HttpClient {
        install(ContentNegotiation){
            json()
        }
    }

    private val baseUrl = "https://api.nasa.gov/planetary/apod"
    private val _apiKey = "FF8ddL82SYc1RWau7pAT9j2FGNBDfCtYe4f2s7IL"

    var nasaImages = MutableStateFlow(listOf<NasaApiResponse>())

    sealed class State {
        data object Init : State()
        data object Loading : State()
        data class Error(val error: ServerError) : State()
        data object Success : State()
    }

    fun getDataByDate(date: String) {
        screenModelScope.launch {
            val url = if(date.isNotEmpty()) {
                "$baseUrl?api_key=$_apiKey&date=$date"
            } else {
                "$baseUrl?api_key=$_apiKey"
            }

            mutableState.value = State.Loading
            val response =
                httpClient.get(url){
                    headers {
                        append("Content-Type", "application/json")
                    }
                }

            if (response.status.value == 200) {
                val data = response.body<NasaApiResponse>()
                nasaImages.value = emptyList()
                val currentList = nasaImages.value.toMutableList()
                currentList.add(data)
                nasaImages.value = currentList
                mutableState.value = State.Success
            } else {
                val errorResponse = response.body<ServerError>()
                mutableState.value = State.Error(errorResponse)
            }
        }
    }

    fun getDataByDateRange(startDate: String, endDate: String) {
        screenModelScope.launch {
            val url = "$baseUrl?api_key=$_apiKey&start_date=$startDate&end_date=$endDate"

            mutableState.value = State.Loading
            val response =
                httpClient.get(url){
                    headers {
                        append("Content-Type", "application/json")
                    }
                }

            if (response.status.value == 200) {
                val nasaImageList: List<NasaApiResponse> = response.body()
                nasaImages.value = nasaImageList
                mutableState.value = State.Success
            } else {
                val errorResponse = response.body<ServerError>()
                mutableState.value = State.Error(errorResponse)
            }
        }
    }

    fun randomPicture() {
        screenModelScope.launch {
            val url = "$baseUrl?api_key=$_apiKey&count=1"

            mutableState.value = State.Loading
            val response =
                httpClient.get(url){
                    headers {
                        append("Content-Type", "application/json")
                    }
                }

            if (response.status.value == 200) {
                val nasaImageList: List<NasaApiResponse> = response.body()
                nasaImages.value = nasaImageList
                mutableState.value = State.Success
            } else {
                val errorResponse = response.body<ServerError>()
                mutableState.value = State.Error(errorResponse)
            }
        }
    }
}