package com.example.theloffle.features.diario.presentation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.theloffle.features.diario.domain.model.FoodPages

import androidx.compose.material3.Text


@Composable
fun RegisterBlock(
    comidaSeleccionada: FoodPages,
    onClose: () -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
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

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onClose) {
            Text("Cerrar")
        }
    }
}