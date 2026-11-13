package com.example.theloffle.features.inicio.presentation.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import theloffle.shared.generated.resources.Res
import theloffle.shared.generated.resources.circle_confirmation
import theloffle.shared.generated.resources.circle_error
import com.example.theloffle.core.UI_System.theme.AppColors
import theloffle.shared.generated.resources.logo

@Composable
fun LogInScreen(
    backClick: () -> Unit,
    logInUser: (String, String) -> Unit,
    isLoading: Boolean,
    isSuccess: Boolean?,
    resetLogInState: () -> Unit,
    onRegisterClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val inputsValid = email.isNotBlank() && password.isNotBlank()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(70.dp))

        Text(
            text = "TheLoffle",
            color = AppColors.textPrimary,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        )

        Image(
            painter = painterResource(Res.drawable.logo),
            contentDescription = "TheLoffle Logo",
            modifier = Modifier.size(75.dp)
        )

        Spacer(modifier = Modifier.height(70.dp))

        if (isLoading) {

            CircularProgressIndicator(color = AppColors.textPrimary)

        } else if (isSuccess == true) {

            Image(
                painter = painterResource(Res.drawable.circle_confirmation),
                contentDescription = "Login successful",
                modifier = Modifier.size(80.dp)
            )

            Text(
                text = "Login successful!",
                color = Color.Black
            )

        } else if (isSuccess == false) {

            Image(
                painter = painterResource(Res.drawable.circle_error),
                contentDescription = "Login failed",
                modifier = Modifier.size(80.dp)
            )

            Text(
                text = "Incorrect email or password!",
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = resetLogInState,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.buttonPrimary,
                    contentColor = AppColors.textPrimary)
            ) {
                Text("Try Again")
            }

        } else {

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = AppColors.textPrimary,
                    unfocusedTextColor = AppColors.textPrimary,
                    focusedLabelColor = AppColors.buttonSecondary,
                    unfocusedLabelColor = AppColors.textPrimary,
                    focusedBorderColor = AppColors.buttonSecondary,
                    unfocusedBorderColor = AppColors.buttonPrimary,
                    focusedContainerColor = AppColors.background,
                    unfocusedContainerColor = AppColors.background
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = AppColors.textPrimary,
                    unfocusedTextColor = AppColors.textPrimary,
                    focusedLabelColor = AppColors.buttonSecondary,
                    unfocusedLabelColor = AppColors.textPrimary,
                    focusedBorderColor = AppColors.buttonSecondary,
                    unfocusedBorderColor = AppColors.buttonPrimary,
                    focusedContainerColor = AppColors.background,
                    unfocusedContainerColor = AppColors.background
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Don't have an account? Register",
                color = AppColors.buttonSecondary,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onRegisterClick()
                    }
                    .padding(top = 8.dp)
            )


            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { logInUser(email, password) },
                enabled = inputsValid && !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.buttonPrimary,
                    contentColor = AppColors.textPrimary
                )
            ) {
                Text("Log in")
            }
        }
    }
}