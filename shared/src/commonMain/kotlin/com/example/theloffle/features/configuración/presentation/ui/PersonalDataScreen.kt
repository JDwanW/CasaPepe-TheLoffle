
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
import org.jetbrains.compose.resources.stringResource
import theloffle.shared.generated.resources.Res
import theloffle.shared.generated.resources.arrow_back
import theloffle.shared.generated.resources.auth_password
import theloffle.shared.generated.resources.personal_data_email
import theloffle.shared.generated.resources.personal_data_name
import theloffle.shared.generated.resources.personal_data_surname
import theloffle.shared.generated.resources.personal_data_title
import theloffle.shared.generated.resources.personal_data_username
import theloffle.shared.generated.resources.settings_back
import theloffle.shared.generated.resources.settings_change_password_subtitle

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
            //el icono para volver a la configuración
            Icon(
                painter = painterResource(Res.drawable.arrow_back),
                contentDescription = stringResource(Res.string.settings_back),
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(40.dp)
                    .clickable { onBackClick() },
                tint = Color.Black
            )

            //Titúlo de la pagina -> Configuración
            Text(
                text = stringResource(Res.string.auth_password),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }


        Spacer(Modifier.height(30.dp))

        //para mostrar el nombre
        PersonalDataItem(
            title = stringResource(Res.string.personal_data_name),
            value = uiState.name
        )

        //para mostrar el apellido
        PersonalDataItem(
            title = stringResource(Res.string.personal_data_surname),
            value = uiState.surname
        )

        //para mostrar el username
        PersonalDataItem(
            title = stringResource(Res.string.personal_data_username),
            value = uiState.username
        )

        //para mostrar la email
        PersonalDataItem(
            title = stringResource(Res.string.personal_data_email),
            value = uiState.email
        )
    }
}

//elementos para el Screen
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
