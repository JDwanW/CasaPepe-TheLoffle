package com.example.theloffle

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.theloffle.ui.components.BottomBar
import com.example.theloffle.ui.home.HomeScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        Scaffold(
            bottomBar = {BottomBar()}
        ){
            // Margen para la BottomBar
            innerPadding -> HomeScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}