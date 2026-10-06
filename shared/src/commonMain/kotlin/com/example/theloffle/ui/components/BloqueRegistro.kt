package com.example.theloffle.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
// Para implementar el button
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
//
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
// Para todos los recursos
import theloffle.shared.generated.resources.Res
import theloffle.shared.generated.resources.recipe_filled
import theloffle.shared.generated.resources.recipe_unfilled


@Composable
fun BloqueRegistro(
    modifier: Modifier = Modifier,
    onComidaClick: (String) -> Unit
) {
    // Colores para los botones
    val registradoColor = Color(0xFF4CAF50)
    val pendienteColor = Color(0xFFF44336)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Color.LightGray,
                RoundedCornerShape(10.dp)
            )
            .navigationBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    onComidaClick("Desayuno")
                },
                modifier = Modifier.size(60.dp),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = registradoColor
                )
            ) {
                Image(
                    painter = painterResource(
                        Res.drawable.recipe_filled
                    ),
                    contentDescription = "Desayuno",
                    modifier = Modifier.size(60.dp)
                )
            }

            Button(
                onClick = {
                    onComidaClick("Merienda")
                },
                modifier = Modifier.size(60.dp),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = pendienteColor
                )
            ) {
                Image(
                    painter = painterResource(
                        Res.drawable.recipe_unfilled
                    ),
                    contentDescription = "Merienda",
                    modifier = Modifier.size(60.dp)
                )
            }

            Button(
                onClick = {
                    onComidaClick("Comida")
                },
                modifier = Modifier.size(60.dp),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = pendienteColor
                )
            ) {
                Image(
                    painter = painterResource(
                        Res.drawable.recipe_unfilled
                    ),
                    contentDescription = "Comida",
                    modifier = Modifier.size(60.dp)
                )
            }

            Button(
                onClick = {
                    onComidaClick("Merienda")
                },
                modifier = Modifier.size(60.dp),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = pendienteColor
                )
            ) {
                Image(
                    painter = painterResource(
                        Res.drawable.recipe_unfilled
                    ),
                    contentDescription = "Merienda",
                    modifier = Modifier.size(60.dp)
                )
            }

            Button(
                onClick = {
                    onComidaClick("Cena")
                },
                modifier = Modifier.size(60.dp),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = pendienteColor
                )
            ) {
                Image(
                    painter = painterResource(
                        Res.drawable.recipe_unfilled
                    ),
                    contentDescription = "Cena",
                    modifier = Modifier.size(60.dp)
                )
            }
        }
    }
}
