package com.example.theloffle.features.inicio.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.theloffle.features.inicio.presentation.model.LogInViewModell
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun LogInScreenRoot(
    viewModel: LogInViewModell,
    backClick: () -> Unit,
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val isSuccess by viewModel.isSuccess.collectAsState()

    LaunchedEffect(isSuccess) {
        if (isSuccess == true) {
            delay(2.seconds)
            onLoginSuccess()
        }
    }

    LogInScreen(
        backClick = backClick,
        logInUser = { email, password ->
            viewModel.login(email, password)
        },
        isLoading = isLoading,
        isSuccess = isSuccess,
        resetLogInState = {
            viewModel.resetLogInState()
        },
        onRegisterClick = onRegisterClick
    )
}