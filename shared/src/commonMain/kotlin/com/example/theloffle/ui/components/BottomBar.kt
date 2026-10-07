package com.example.theloffle.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import theloffle.shared.generated.resources.Res
import theloffle.shared.generated.resources.main_unfilled
import theloffle.shared.generated.resources.main_filled
import theloffle.shared.generated.resources.recipe_unfilled
import theloffle.shared.generated.resources.recipe_filled
import theloffle.shared.generated.resources.search_unfilled
import theloffle.shared.generated.resources.search_filled
import theloffle.shared.generated.resources.profile_unfilled
import theloffle.shared.generated.resources.profile_filled

@Composable
fun BottomBar(){
    Row(
        modifier = Modifier.fillMaxWidth()
            // En el color las dos primeros digitos son la opacidad y el resto el color
            // Por si hay que cambiarlo luego
            .background(Color(0xFFB8E6A3), RoundedCornerShape(10.dp))
            .navigationBarsPadding() // Para el margen con el simbolo del sistema
            .height(55.dp),

        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        // Cada imagen de la barra
        Image(
            painter = painterResource(Res.drawable.main_filled),
            contentDescription = "Inicio",
            modifier = Modifier.size(60.dp)
        )
        Image(
            painter = painterResource(Res.drawable.recipe_unfilled),
            contentDescription = "Recetas",
            modifier = Modifier.size(60.dp)
        )
        Image(
            painter = painterResource(Res.drawable.search_unfilled),
            contentDescription = "Buscar",
            modifier = Modifier.size(60.dp)
        )
        Image(
            painter = painterResource(Res.drawable.profile_unfilled),
            contentDescription = "Usuario",
            modifier = Modifier.size(60.dp)
        )
    }
}