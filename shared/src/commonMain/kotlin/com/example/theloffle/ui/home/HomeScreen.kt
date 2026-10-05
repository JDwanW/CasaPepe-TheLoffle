package com.example.theloffle.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.theloffle.ui.components.BloqueCard
import com.example.theloffle.ui.components.Header

@Composable
// Le pasamos como parametro el modifier que le define los margenes
fun HomeScreen(modifier: Modifier = Modifier){ // Valor por defecto vacio
    Column(
        // Definimos el tamaño maximo, margenes y separaciones entre bloques
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 26.dp)
            .padding(bottom = 26.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    )
    {
        Header()
        // Los cuatro bloques, les pasamos el modifier con la altura
        BloqueCard(Modifier.height(80.dp))
        BloqueCard(Modifier.height(80.dp))
        BloqueCard(Modifier.height(80.dp))
        BloqueCard(Modifier.weight(1f)) // El bloque final de recetas se lleva lo que sobra
                                        // ASi lo ajustamos a cualquier movil
    }
}