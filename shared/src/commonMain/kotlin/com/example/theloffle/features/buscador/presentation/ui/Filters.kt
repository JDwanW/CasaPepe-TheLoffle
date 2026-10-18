package com.example.theloffle.features.buscador.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.theloffle.core.UI_System.theme.AppColors

@Composable
fun Filters(modifier: Modifier = Modifier.Companion){
    // Row de opciones de busqueda
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .background(AppColors.buttonSecondary, RoundedCornerShape(10.dp))
                .fillMaxHeight()
                .padding(horizontal = 10.dp)
                .padding(vertical = 10.dp)
        ) {
            Text("Filtro 1")
            Text("Filtro 2")

        }
        Column(
            modifier = Modifier
                .weight(2f)
                .background(
                    AppColors.buttonPrimary,
                    androidx.compose.foundation.shape.RoundedCornerShape(10.dp)
                )
                .fillMaxHeight()
                .padding(horizontal = 10.dp)
                .padding(vertical = 10.dp)
        ) {
            Text("Relacionado con los filtros que metas")
            Text("Ejemplo: Pones Filtro Italiano saldran tags de Pizza, Pasta, etc")
        }
    }
}