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
import com.example.theloffle.ui.components.BloqueRegistro
import com.example.theloffle.core.UI_System.components.BloqueCard
import com.example.theloffle.ui.components.PostBlock
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
// Para implementar remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
//
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.unit.sp
import page.FoodPages

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
            // Los cuatro bloques, les pasamos el modifier con la altura
            BloqueRegistro(
                modifier = Modifier.height(80.dp),
                onMealSelected = { comida ->
                    comidaSeleccionada = comida
                }
            )
            BloqueCard(Modifier.height(80.dp))
            BloqueCard(Modifier.height(80.dp))
            // Todo : scrollbar with 1f length and then postBlocks
            PostBlock(modifier = Modifier.height(480.dp)) // El bloque final de recetas se lleva lo que sobra
            // ASi lo ajustamos a cualquier movil
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
                    .background(
                        Color.Black.copy(alpha = 0.5f)
                    )
                    .clickable {
                        comidaSeleccionada = FoodPages.NONE
                    }
            )
        }
        // PANEL EXPANDIDO
        // Todo : Make the container dynamicly and a Class FillMeal.kt to create the UI
        AnimatedVisibility(
            visible = comidaSeleccionada != FoodPages.NONE,
            enter = fadeIn() + expandVertically(expandFrom = Alignment.Top),
            exit = fadeOut() + shrinkVertically(shrinkTowards = Alignment.Top),
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Box(
                // Habria que ver de alguna manera hacerlo dinamico en vez de hardcode(los dp)
                modifier = Modifier
                    .padding(top = 150.dp)
                    .width(400.dp)
                    .height(600.dp)
                    .background(Color.White, RoundedCornerShape(10.dp))
            ) {
                //Todo : We need another val to take control of each meal, if this one has been added
                //Todo : the button of that meal should be AppColor.foodDone, eoc AppColor.foodMissed
                when (comidaSeleccionada) {
                    FoodPages.NONE -> {}
                    FoodPages.BREAKFAST -> {
                        Text("Desayuno", fontSize = 24.sp)
                    }
                    FoodPages.SNACK1 -> {
                        Text("Merienda", fontSize = 24.sp)
                    }
                    FoodPages.LUNCH -> {
                        Text("Comida", fontSize = 24.sp)
                    }
                    FoodPages.SNACK2 -> {
                        Text("Merienda2", fontSize = 24.sp)
                    }
                    FoodPages.DINNER -> {
                        Text("Cena", fontSize = 24.sp)
                    }
                }
            }
        }
    }
}