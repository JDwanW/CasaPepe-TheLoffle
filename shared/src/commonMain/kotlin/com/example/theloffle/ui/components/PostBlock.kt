package com.example.theloffle.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


//Todo : create a box with
//Todo : 3 rows
//Todo : 1 row: profile picture + profile username + unfollow profile
//Todo : 2 row: image of the recipe
//Todo : 3 row: recipe rating from 0 ... 5 in double
@Composable
// Pasamos un modifier como parametro para que se defina en el homescreen la altura de los paneles
fun PostBlock(modifier: Modifier = Modifier){
    Box(
        // Maxima anchura, altura que le pasemos y gris redondeado
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFB8E6A3), RoundedCornerShape(10.dp))
    )
}