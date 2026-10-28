package com.example.theloffle.features.inicio.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.theloffle.core.UI_System.theme.AppColors
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen (
    backClick: () -> Unit,
    registerUser: (email: String, password: String, name:String, surname:String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val nameValid = name.length > 2
    val surnameValid = name.length > 3
    val emailValid = email.length > 5
    val passwordValid = password.length >= 8
    val inputsValid = nameValid && surnameValid && emailValid && passwordValid

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    color = AppColors.buttonPrimary
                )

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "Registering with this Mail:\n$email",
                    textAlign = TextAlign.Center
                )
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text("You can register here:")

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") }
            )

            OutlinedTextField(
                value = surname,
                onValueChange = { surname = it },
                label = { Text("Surname") }
            )

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
                onClick = {
                    scope.launch {
                        isLoading = true
                        delay(2000)
                        isLoading = false
                        registerUser(email, password, name, surname)
                    }

                },
                enabled = inputsValid,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF7FC97F)
                )
            )
            {
                Text("Create an Account")
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
}

