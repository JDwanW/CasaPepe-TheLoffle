package com.example.theloffle.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import page.AppPages
import theloffle.shared.generated.resources.Res
import theloffle.shared.generated.resources.main_unfilled
import theloffle.shared.generated.resources.main_filled
import theloffle.shared.generated.resources.recipe_unfilled
import theloffle.shared.generated.resources.recipe_filled
import theloffle.shared.generated.resources.search_unfilled
import theloffle.shared.generated.resources.search_filled
import theloffle.shared.generated.resources.profile_unfilled
import theloffle.shared.generated.resources.profile_filled
import theme.AppColors

@Composable
fun BottomBar(
    pageSeleccionada: AppPages,
    onPageClick: (AppPages) -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(
                AppColors.buttonPrimary,
                RoundedCornerShape(10.dp)
            )
            .navigationBarsPadding(), // Para el margen con el simbolo del sistema
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    onPageClick(AppPages.HOME)
                },
                modifier = Modifier.size(45.dp),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (pageSeleccionada == AppPages.HOME)
                        AppColors.buttonSecondary
                    else
                        AppColors.buttonPrimary
                )
            ) {
                Image(
                    painter = painterResource(
                        if (pageSeleccionada == AppPages.HOME)
                            Res.drawable.main_filled
                        else
                            Res.drawable.main_unfilled
                    ),
                    contentDescription = "Inicio",
                    modifier = Modifier.size(45.dp)
                )
            }
            Button(
                onClick = {
                    onPageClick(AppPages.RECIPE)
                },
                modifier = Modifier.size(45.dp),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (pageSeleccionada == AppPages.RECIPE)
                        AppColors.buttonSecondary
                    else
                        AppColors.buttonPrimary
                )
            ) {
                Image(
                    painter = painterResource(
                        if (pageSeleccionada == AppPages.RECIPE)
                            Res.drawable.recipe_filled
                        else
                            Res.drawable.recipe_unfilled
                    ),
                    contentDescription = "Recetas",
                    modifier = Modifier.size(45.dp)
                )
            }
            Button(
                onClick = {
                    onPageClick(AppPages.SEARCH)
                },
                modifier = Modifier.size(45.dp),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (pageSeleccionada == AppPages.SEARCH)
                        AppColors.buttonSecondary
                    else
                        AppColors.buttonPrimary
                )
            ) {
                Image(
                    painter = painterResource(
                        if (pageSeleccionada == AppPages.RECIPE)
                            Res.drawable.search_filled
                        else
                            Res.drawable.search_unfilled
                    ),
                    contentDescription = "Search",
                    modifier = Modifier.size(45.dp)
                )
            }
            Button(
                onClick = {
                    onPageClick(AppPages.PROFILE)
                },
                modifier = Modifier.size(45.dp),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (pageSeleccionada == AppPages.PROFILE)
                        AppColors.buttonSecondary
                    else
                        AppColors.buttonPrimary
                )
            ) {
                Image(
                    painter = painterResource(
                        if (pageSeleccionada == AppPages.PROFILE)
                            Res.drawable.profile_filled
                        else
                            Res.drawable.profile_unfilled
                    ),
                    contentDescription = "Usuario",
                    modifier = Modifier.size(45.dp)
                )
            }
        }
    }
}