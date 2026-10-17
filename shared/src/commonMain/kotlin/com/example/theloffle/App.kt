package com.example.theloffle

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.theloffle.core.UI_System.components.BottomBar

import com.example.theloffle.ui.screen.SearchScreen
import com.example.theloffle.ui.screen.HomeScreen
import com.example.theloffle.core.UI_System.theme.AppColors


@Composable
@Preview
fun App() {
    //MaterialTheme { We gonna use this after we create our LightSchemeTheme and DarkSchemeTheme
        var pageSeleccionada by remember { mutableStateOf(AppPages.HOME)}
        Scaffold(
            containerColor = AppColors.background,
            bottomBar = {
                BottomBar(
                    pageSeleccionada = pageSeleccionada,
                    onPageClick = { page ->
                        pageSeleccionada = page
                    }
                )
            }
        ){
            // Margen para la BottomBar
            innerPadding ->
            when (pageSeleccionada) {
                AppPages.HOME -> {HomeScreen(modifier = Modifier.padding(innerPadding))}
                AppPages.RECIPE -> {}
                AppPages.SEARCH -> {SearchScreen(modifier = Modifier.padding((innerPadding)))}
                AppPages.PROFILE -> {}
            }
        }
    //}
}