package com.example.theloffle.features.perfil.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import theloffle.shared.generated.resources.Res
import theloffle.shared.generated.resources.settings

@Composable
fun ProfileScreen(
    onSettingsClick: () -> Unit
){
    IconButton(
        onClick = onSettingsClick
    ) {
        Image(
            painter = painterResource(Res.drawable.settings),
            contentDescription = "Open settings",
            modifier = Modifier.size(28.dp)
        )
    }
}