package com.example.theloffle.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import theloffle.shared.generated.resources.Res
import theloffle.shared.generated.resources.logo

@Composable
fun Header(){
    Row(
        // La Cabecera el una fila que esta centrada
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Image(
            // La imagen
            painter = painterResource(Res.drawable.logo),
            contentDescription = "Logo The Loffel",
            modifier = Modifier.size(45.dp)
        )
        // Espacio entre logo y nombre y el nombre
        Spacer(Modifier.width(12.dp))
        Text("The Löffel", fontSize = 24.sp)
    }
}