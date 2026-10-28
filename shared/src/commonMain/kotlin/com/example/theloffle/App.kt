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
import com.example.theloffle.core.UI_System.theme.AppColors

import com.example.theloffle.features.buscador.presentation.ui.SearchScreen
import com.example.theloffle.ui.screen.HomeScreen
import com.example.theloffle.features.inicio.presentation.ui.PrincipalScreen
import com.example.theloffle.features.inicio.presentation.ui.RegisterScreen
import com.example.theloffle.features.inicio.presentation.ui.SignInScreen
import kotlinx.coroutines.delay


@Composable
@Preview
fun App() {
    //MaterialTheme { We gonna use this after we create our LightSchemeTheme and DarkSchemeTheme
        var pageSeleccionada by remember { mutableStateOf(AppPages.PRINCIPAL)}
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
            // Todo : create onSigInClick and RegisterOne, Logic of Register/LogIn/SignIn
            innerPadding ->
            when (pageSeleccionada) {
                // TODO: conectar con SignIn y Register cuando esté hecha la lógica (tarea de login/register)
                AppPages.PRINCIPAL -> { PrincipalScreen(
                    modifier = Modifier.padding(innerPadding),
                    onSignInClick = {
                        pageSeleccionada = AppPages.SIGNIN
                    },
                    onRegisterClick = {
                        pageSeleccionada = AppPages.REGISTER
                    }
                ) }
                AppPages.SIGNIN -> {
                    SignInScreen(
                        backClick = {
                            pageSeleccionada = AppPages.PRINCIPAL
                        },
                        signInUser = { email, password ->
                            pageSeleccionada = AppPages.HOME
                        }
                    )
                }

                AppPages.REGISTER -> {
                    RegisterScreen(
                        backClick = {
                            pageSeleccionada = AppPages.PRINCIPAL
                    },
                        registerUser = { email, password, name, surname ->
                            pageSeleccionada = AppPages.HOME
                        }
                    )
                }
                AppPages.HOME -> {HomeScreen(modifier = Modifier.padding(innerPadding))}
                AppPages.RECIPE -> {}
                AppPages.SEARCH -> {SearchScreen(modifier = Modifier.padding((innerPadding)))}
                AppPages.PROFILE -> {}

            }
        }
    //}
}