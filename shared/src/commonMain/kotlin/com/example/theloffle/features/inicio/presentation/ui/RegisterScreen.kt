package com.example.theloffle.features.inicio.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import com.example.theloffle.core.UI_System.theme.AppColors
import org.jetbrains.compose.resources.painterResource
import theloffle.shared.generated.resources.Res
import theloffle.shared.generated.resources.circle_confirmation
import theloffle.shared.generated.resources.circle_error
import theloffle.shared.generated.resources.logo


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
    isSuccess: Boolean?,
    resetRegistrationState: () -> Unit

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
            CircularProgressIndicator()
        } else if (isSuccess == true) {

            Image(
                painter = painterResource(Res.drawable.circle_confirmation),
                contentDescription = "Registration successful",
                modifier = Modifier.size(80.dp)

            )
            Text("Registration successful!")
                
        } else if (isSuccess == false) {
            Image(
                painter = painterResource(Res.drawable.circle_error),
                contentDescription = "Registration successful",
                modifier = Modifier.size(80.dp)

            )
            Text("Registration went wrong - Email is already used!")

            Button(
                onClick = resetRegistrationState,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.buttonPrimary,
                    contentColor = AppColors.textPrimary)

            ) {
                Text("Try Again")
            }

        } else {

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
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
                value = surname,
                onValueChange = { surname = it },
                label = { Text("Surname") },
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
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },visualTransformation = PasswordVisualTransformation(),
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
                text = "Back to LogIn",
                color = AppColors.buttonSecondary,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        backClick()
                    }
                    .padding(top = 8.dp)
            )


            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { registerUser(name, surname, email, password) },
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
                Text("Register")
            }
        }
    }
}