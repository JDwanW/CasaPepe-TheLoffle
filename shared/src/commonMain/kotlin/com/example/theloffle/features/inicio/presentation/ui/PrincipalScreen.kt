package com.example.theloffle.features.inicio.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.theloffle.core.UI_System.components.Header

@Composable
fun PrincipalScreen (
    modifier: Modifier,
    onSignInClick: () -> Unit,
    onRegisterClick: () -> Unit
) {


    Column(
        modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "WELCOME TO ",
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Header()

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "TheLöffel",
                fontSize = 40.sp
            )
        }


        Spacer(modifier = Modifier
            .height(100.dp))

        Button(
            onClick = onSignInClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7FC97F)
            ),

            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(350.dp)
                .height(56.dp))
        {
            Text("Sign in")
        }

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = onRegisterClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7FC97F)
            ),

            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(350.dp)
                .height(56.dp)
        ) {
            Text("Register")
        }


    }
}