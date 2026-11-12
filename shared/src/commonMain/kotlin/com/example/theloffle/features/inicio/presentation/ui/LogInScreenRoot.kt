package com.example.theloffle.features.inicio.presentation.ui

import androidx.compose.runtime.Composable
import com.example.theloffle.features.inicio.presentation.model.LogInViewModell

@Composable
fun LogInScreenRoot(
    viewModel: LogInViewModell,
    backClick: () -> Unit

) {
    LogInScreen(
        backClick = backClick,
        logInUser = {email, password ->
            viewModel.login(email,password)
        },
        isLoading = true
    )
}
