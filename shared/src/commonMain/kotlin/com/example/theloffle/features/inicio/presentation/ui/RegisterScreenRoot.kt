package com.example.theloffle.features.inicio.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.theloffle.features.inicio.presentation.model.RegisterViewModel
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun RegisterScreenRoot(
    viewModel: RegisterViewModel,
    backClick: () -> Unit,
    onRegisterSuccess: () -> Unit,


    ) {
    val isLoading by viewModel.isLoading.collectAsState()
    val isSuccess by viewModel.isSuccess.collectAsState()

    LaunchedEffect(isSuccess) {
        if (isSuccess == true) {
            delay(2.seconds)
            onRegisterSuccess()
        }
    }

    RegisterScreen(
        backClick = backClick,
        registerUser = {email,password,name,surname ->
            viewModel.register(email,password,name,surname)
        },
        resetRegistrationState = { ->
            viewModel.resetRegistrationState()
        },
        isLoading = isLoading,
        isSuccess = isSuccess,
    )
}