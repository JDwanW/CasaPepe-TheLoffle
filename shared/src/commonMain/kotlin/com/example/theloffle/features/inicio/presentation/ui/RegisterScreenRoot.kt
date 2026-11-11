package com.example.theloffle.features.inicio.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.theloffle.features.inicio.presentation.model.RegisterViewModel

@Composable
fun RegisterScreenRoot(
    viewModel: RegisterViewModel,
    backClick: () -> Unit,

) {
    val isLoading by viewModel.isLoading.collectAsState()

    RegisterScreen(
        backClick = backClick,
        isLoading = isLoading,
        registerUser = {email,password,name,surname ->
            viewModel.register(email,password,name,surname)
        }
    )
}