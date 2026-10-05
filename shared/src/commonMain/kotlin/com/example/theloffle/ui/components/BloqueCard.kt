package com.example.theloffle.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BloqueCard(altura: Float){
    Box(
        // Maxima anchura, altura que le pasemos y gris redondeado
        modifier = Modifier
            .fillMaxWidth()
            .height(altura.dp)
            .background(Color.LightGray, RoundedCornerShape(10.dp))
    )
}