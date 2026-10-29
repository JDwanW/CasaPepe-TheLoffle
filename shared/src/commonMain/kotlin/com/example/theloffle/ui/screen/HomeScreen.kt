package com.example.theloffle.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
// Blocks
import com.example.theloffle.features.diario.presentation.ui.RegisterSection
import com.example.theloffle.features.diario.presentation.ui.RegisterBlock
import com.example.theloffle.core.UI_System.components.BloqueCard
import com.example.theloffle.core.UI_System.components.Header
// Para animatedVisibility && overlay
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
// Para implementar remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
//
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.theloffle.features.diario.domain.FoodPages
import com.example.theloffle.features.publicaciones.presentation.ui.ScrollBlock

@Composable
// Le pasamos como parametro el modifier que le define los margenes
fun HomeScreen(modifier: Modifier = Modifier){ // Valor por defecto vacio

    var comidaSeleccionada by remember { mutableStateOf<FoodPages>(FoodPages.NONE) }
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            // Definimos el tamaño maximo, margenes y separaciones entre bloques
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 26.dp)
                .padding(bottom = 26.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Header()
            RegisterSection(
                modifier = Modifier.height(80.dp),
                onMealSelected = { comida ->
                    comidaSeleccionada = comida
                }
            )
            //StadisticSection(modifier = Modifier.height(80.dp),)
            ScrollBlock(modifier = Modifier.fillMaxHeight(1f))
        }
        // OVERLAY
        AnimatedVisibility(
            visible = comidaSeleccionada != FoodPages.NONE,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable {
                        comidaSeleccionada = FoodPages.NONE
                    }
            )
        }

        // PANEL EXPANDIDO
        AnimatedVisibility(
            visible = comidaSeleccionada != FoodPages.NONE,
            enter = fadeIn() +
                    expandVertically(expandFrom = Alignment.Top),
            exit = fadeOut() +
                    shrinkVertically(shrinkTowards = Alignment.Top),
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 150.dp)
                    .width(400.dp)
                    .height(600.dp)
                    .background(
                        Color.White,
                        RoundedCornerShape(10.dp)
                    )
            ) {
                RegisterBlock(
                    comidaSeleccionada = comidaSeleccionada,
                    onClose = {
                        comidaSeleccionada = FoodPages.NONE
                    }
                )
            }
        }
    }
}