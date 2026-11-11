package com.example.theloffle.features.inicio.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun SignInScreen(
    backClick: () -> Unit,
    registerUser: (
            String,
            String,
            String,
            String
            ) -> Unit,
            isLoading: Boolean
    ) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val passwordValid = password.length >= 8

    Column(
        modifier =
            Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text("You can sign in here:")

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-Mail") }
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") }
        )

        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7FC97F)
            )

        ) {
            Text("Sign in")
        }

        Button(
            onClick = backClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7FC97F)
            )
        ) {
            Text("Back")
        }
    }
}