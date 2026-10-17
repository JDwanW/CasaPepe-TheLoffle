package com.example.theloffle.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import theloffle.shared.generated.resources.Res
import theloffle.shared.generated.resources.search_filled
import com.example.theloffle.core.UI_System.theme.AppColors

// Pensar si vamos a tener mas barras de busqueda o solo una porque
// si tenemos mas de una a lo mejor merece la pena refactorizarla para
// hacerla generica y meterla en el core. Por ahora la dejo en feature
@Composable
fun SearchBlock(modifier: Modifier = Modifier){
    var texto by remember { mutableStateOf("") }    // Variable que guarda lo que el

    //Box de Busqueda                                      // usuario busca
    Box(
        modifier = modifier
            .fillMaxWidth(0.98f)  // Ocupe el 98%
            .height(90.dp)
            .background(Color.White, RoundedCornerShape(15.dp))
            .border(10.dp, AppColors.buttonSecondary, RoundedCornerShape(15.dp))
    )
    {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp),
            // Para los textfield tienen un parametro para poner el fondo transparente
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true, // Lo he usado para centrar el texto
            value = texto,
            onValueChange = { texto = it },
            textStyle = TextStyle(fontSize = 20.sp),
            placeholder = { Text("Busca recetas...", fontSize = 20.sp) },
            // Esto es el logo del buscador, es un parametro propio de los textfield
            trailingIcon = {
                IconButton(onClick = {/*/ Todo en un futuro que busque */ }) {
                    Image(
                        painter = painterResource(Res.drawable.search_filled),
                        contentDescription = "Buscar",
                        modifier = Modifier
                            .size(70.dp)
                            .padding(horizontal = 5.dp)
                    )
                }
            }
        )
    }
}