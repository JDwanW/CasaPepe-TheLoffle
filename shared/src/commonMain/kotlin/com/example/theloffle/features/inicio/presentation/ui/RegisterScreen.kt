package com.example.theloffle.features.inicio.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp

@Composable
fun RegisterScreen (
    backClick: () -> Unit,
    registerUser: (
        String,
        String,
        String,
        String
    ) -> Unit,
    isLoading: Boolean,
    isSuccess: Boolean?

) {

    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val nameValid = name.length > 2
    val surnameValid = surname.length > 3
    val emailValid = email.length > 5
    val passwordValid = password.length >= 8
    val inputsValid = nameValid && surnameValid && emailValid && passwordValid




    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        if (isLoading) {
            CircularProgressIndicator()
        } else if (isSuccess == true) {

            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = "Registration successful",
                tint = Color.Green,
                modifier = Modifier.size(80.dp)
            )

            Text("Registration successful!")
        } else if (isSuccess == false) {

        } else {


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
            if (!passwordValid && !password.isEmpty()) {
                Text("Password is not valid, need to have more than 8 signs!")
            }


            Button(
                onClick = {
                    registerUser(email, password, name, surname)
                },
                enabled = inputsValid && !isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF7FC97F)
                )

            ) {

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