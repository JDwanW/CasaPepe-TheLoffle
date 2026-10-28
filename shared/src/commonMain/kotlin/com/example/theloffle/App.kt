package com.example.theloffle

import Route
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.theloffle.core.UI_System.components.BottomBar
import com.example.theloffle.core.UI_System.theme.AppColors

import com.example.theloffle.features.buscador.presentation.ui.SearchScreen
import com.example.theloffle.ui.screen.HomeScreen
import com.example.theloffle.features.inicio.presentation.ui.PrincipalScreen

import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Composable
@Preview
fun App() {
    //MaterialTheme { We gonna use this after we create our LightSchemeTheme and DarkSchemeTheme
        var pageSeleccionada by remember { mutableStateOf(AppPages.PRINCIPAL)}
        val navController = rememberNavController()

        Scaffold(
            containerColor = AppColors.background,
        ){
            // Todo : create onSigInClick and RegisterOne, Logic of Register/LogIn/SignIn
            innerPadding ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
            ){
                NavHost(navController = navController, startDestination = Route.HomeScreen) {
                    composable<Route.PrincipalScreen> {
                        PrincipalScreen(
                            modifier = Modifier.padding(innerPadding),
                            onSignInClick = {},
                            onRegisterClick = {}
                        )
                    }
                    composable<Route.HomeScreen> {
                        HomeScreen(modifier = Modifier.padding(innerPadding))
                    }
                    composable<Route.RecipeScreen>{

                    }
                    composable<Route.SearchScreen>{
                        SearchScreen(modifier = Modifier.padding(innerPadding))
                    }
                    composable<Route.ProfileScreen>{

                    }
                    composable<Route.MealRegisterScreen>{

                    }
                    composable<Route.StadisticScreen>{

                    }
                }

                //AppPages.SIGNIN -> {SignInScreen(modifier = Modifier.padding(innerPadding))}
                //AppPages.REGISTER -> {RegisterScreen(modifier = Modifier.padding(innerPadding))}


                // We create bottomBar here so it can be overlay
                BottomBar(
                    pageSeleccionada = pageSeleccionada,
                    onPageClick = { page ->
                        pageSeleccionada = page
                    },
                    modifier = Modifier.align(Alignment.BottomCenter)
                        .offset(y = (-30).dp)
                )
            }
        }
    //}
}
