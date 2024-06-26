package main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import screens.ErrorScreen
import screens.ImageScreen
import screens.LoadingScreen
import shared.StyledDatePickerDialog

class MainScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { MainScreenModel() }
        val state by screenModel.state.collectAsState()
        val showDialog = remember { mutableStateOf(false) }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black
        ) {
            when (state) {
                is MainScreenModel.State.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingScreen()
                    }
                }

                is MainScreenModel.State.Error -> {
                    val errorResponse = (state as MainScreenModel.State.Error).error
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        ErrorScreen(serverError = errorResponse)
                    }
                }

                is MainScreenModel.State.Success -> {
                    val images = screenModel.nasaImages.collectAsState()
                    val currentPage = remember { mutableStateOf(0) }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 36.dp)
                    ) {
                        ImageScreen(
                            image = images.value[currentPage.value],
                            backVisible = currentPage.value != 0 && images.value.size > 1,
                            forwardVisible = currentPage.value < images.value.size - 1,
                            onRandomPicture = { screenModel.randomPicture() },
                            onShowDialog = { showDialog.value = true },
                            onBackPressed = { if (currentPage.value > 0) currentPage.value -= 1 },
                            onForwardPressed = { if (currentPage.value < images.value.size - 1) currentPage.value += 1 }
                        )
                    }

                    if (showDialog.value) {
                        StyledDatePickerDialog(
                            onDismiss = {
                                showDialog.value = false
                            },
                            onDateSelected = { startDate, endDate ->
                                if (endDate.isEmpty()) {
                                    screenModel.getDataByDate(startDate)
                                } else {
                                    screenModel.getDataByDateRange(startDate, endDate)
                                }
                            }
                        )
                    }
                }

                is MainScreenModel.State.Init -> {
                    screenModel.getDataByDate("")
                }
            }
        }
    }
}