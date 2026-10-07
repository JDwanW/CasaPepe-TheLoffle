package com.example.theloffle.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
// Para implementar Fonts
import org.jetbrains.compose.resources.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontStyle
//
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
// Para implementar recursos
import theloffle.shared.generated.resources.Res
import theloffle.shared.generated.resources.logo
import theloffle.shared.generated.resources.logo100
import theloffle.shared.generated.resources.OleoScriptSwashCaps_Bold
import theloffle.shared.generated.resources.OleoScriptSwashCaps_Regular

@Composable
fun Header(){
    val fontRegular = FontFamily(Font(Res.font.OleoScriptSwashCaps_Regular))
    val fontBold = FontFamily(Font(Res.font.OleoScriptSwashCaps_Bold))
    Row(
        // La Cabecera el una fila que esta centrada
        modifier = Modifier.fillMaxWidth().height(100.dp).padding(top = 50.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Image(
            // La imagen
            modifier = Modifier.size(60.dp),
            painter = painterResource(Res.drawable.logo100),
            contentDescription = "Logo The Loffel"
        )
        // Espacio entre logo y nombre y el nombre
        Spacer(Modifier.width(12.dp))
        Text("The Löffel", fontSize = 30.sp, fontFamily = fontBold)
    }
}