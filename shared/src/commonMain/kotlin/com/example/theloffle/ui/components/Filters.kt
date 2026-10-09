package com.example.theloffle.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import theme.AppColors

@Composable
fun Filters(modifier: Modifier = Modifier){
    // Box de opciones de busqueda
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(AppColors.buttonPrimary, RoundedCornerShape(10.dp))
    ){
        Column {  }
        Column {  }
    }
}