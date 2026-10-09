package com.example.theloffle.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SearchBlock(modifier: Modifier = Modifier){
    var texto by remember { mutableStateOf("") }    // Variable que guarda lo que el
                                                            // usuario busca
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(Color.LightGray, RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.Center
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)  // Ocupe el 90%
                .height(70.dp)
                .background(Color.White, RoundedCornerShape(10.dp))
        )
        {
            TextField(
                value = texto,
                onValueChange = {texto = it},
                placeholder = { Text("Busca recetas...") }
            )
        }
    }
}