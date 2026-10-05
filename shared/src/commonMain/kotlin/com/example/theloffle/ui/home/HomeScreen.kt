package com.example.theloffle.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.theloffle.ui.components.BloqueCard

@Composable
fun HomeScreen(){
    Column(
        // Definimos el tamaño maximo, margenes y separaciones entre bloques
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 26.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    )
    {
        // Los cuatro bloques (la f es para decimales que son Floats
        BloqueCard(80f)
        BloqueCard(80f)
        BloqueCard(80f)
        BloqueCard(250f)
    }
}