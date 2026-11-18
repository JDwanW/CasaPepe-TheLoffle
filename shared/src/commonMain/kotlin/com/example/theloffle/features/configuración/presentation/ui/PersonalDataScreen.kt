
package com.example.theloffle.features.configuración.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.theloffle.features.configuración.presentation.model.PersonalDataUiState
import org.jetbrains.compose.resources.painterResource
import theloffle.shared.generated.resources.Res
import theloffle.shared.generated.resources.arrow_back

@Composable
fun PersonalDataScreen(
    uiState: PersonalDataUiState,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(Res.drawable.arrow_back),
                contentDescription = "Volver",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(40.dp)
                    .clickable { onBackClick() },
                tint = Color.Black
            )

            Text(
                text = "Datos personales",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(30.dp))

        PersonalDataItem(
            title = "Nombre",
            value = uiState.name
        )

        PersonalDataItem(
            title = "Apellidos",
            value = uiState.surname
        )

        PersonalDataItem(
            title = "Nombre de usuario",
            value = uiState.username
        )

        PersonalDataItem(
            title = "Correo electrónico",
            value = uiState.email
        )
    }
}

@Composable
private fun PersonalDataItem(
    title: String,
    value: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Text(
            text = title,
            fontSize = 13.sp,
            color = Color.Gray
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF252525)
        )

        Spacer(Modifier.height(12.dp))

        HorizontalDivider(
            color = Color(0xFFE0E0E0)
        )
    }
}
