package com.example.theloffle.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import theme.AppColors

@Composable
fun SearchBlock(modifier: Modifier = Modifier){
    var texto by remember { mutableStateOf("") }    // Variable que guarda lo que el

    //Box de Busqueda                                      // usuario busca
    Box(
        modifier = Modifier
            .fillMaxWidth(0.98f)  // Ocupe el 90%
            .height(90.dp)
            .background(Color.White, RoundedCornerShape(10.dp))
            .border(10.dp, AppColors.buttonPrimary, RoundedCornerShape(30.dp))
    )
    {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .background(Color(0x00FFFFFF), RoundedCornerShape(30.dp)),
            value = texto,
            onValueChange = { texto = it },
            placeholder = { Text("Busca recetas...") }
        )
    }
}