package com.example.theloffle.features.inicio.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
fun SignInScreen(
    backClick: () -> Unit,
    signInUser: (email: String, password: String) -> Unit
    )

    {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember {mutableStateOf(false) }
        val scope = rememberCoroutineScope()


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
                        text = "Signing in with this Mail:\n$email",
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        else {
        Column(
            modifier = Modifier.fillMaxSize(),
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
                onClick = {
                    scope.launch {
                        isLoading = true
                        delay(2000)
                        isLoading = false
                        signInUser(email, password)
                        }
                          },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.buttonSecondary
                )
            ) {
                Text("Sign in")
            }

            Button(
                onClick = backClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.buttonSecondary
                )
            ) {
                Text("Back")
            }
        }
    }
}

