package com.example.theloffle.features.configuración.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
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
import com.example.theloffle.core.UI_System.theme.AppColors
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import theloffle.shared.generated.resources.Res
import theloffle.shared.generated.resources.arrow_back
import theloffle.shared.generated.resources.arrow_right
import theloffle.shared.generated.resources.profile_unfilled


@Composable
fun SettingsScreen(
    backClick : () -> Unit,
    onLogoutClick: () -> Unit,
    showDataPersonal : () -> Unit,
    changePassword : () -> Unit
) {
    Column(
        modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .padding(horizontal = 16.dp)
    )

    {
        //para el Icono a lado del la funcionalidad
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(bottom = 20.dp),
        ) {
            Icon(
                painter = painterResource(Res.drawable.arrow_back),
                contentDescription = "Volver",
                modifier = Modifier
                    .size(50.dp)
                    .clickable { backClick() },
                tint = Color.Black
            )

            Spacer(Modifier.width(16.dp))

            Text(
                text = "Configuración",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )

        }

        //para ver los datos personales
        SettingsItem(
            title = "Datos personales",
            subtitle = "Nombre, usuario y correo electrónico",
            icon = Res.drawable.profile_unfilled,
            onClick = { showDataPersonal() }
        )

        //para cambiar la contrasena
        SettingsItem(
            title = "Cambiar contraseña",
            subtitle = "Seguridad de la cuenta",
            icon = Res.drawable.profile_unfilled,
            onClick = { changePassword() }

            )

        //para ver settings de privacidad / private/public account
        SettingsItem(
            title = "Privacidad",
            subtitle = "Visibilidad del perfil",
            icon = Res.drawable.profile_unfilled,
            onClick = { }
        )

        Spacer(Modifier.height(22.dp))
        SettingsSection("PREFERENCIAS")

        SettingsItem(
            title = "Apariencia",
            subtitle = "Modo claro, oscuro o sistema",
            icon = Res.drawable.profile_unfilled,
            onClick = { }
        )

        //para cambiar el idioma de la applicación
        SettingsItem(
            title = "Idioma",
            subtitle = "Español, Deutsch, English",
            icon = Res.drawable.profile_unfilled,
            onClick = { }
        )

        //para turn on/turn of notificaciónes
        SettingsItem(
            title = "Notificaciones",
            subtitle = "Gestionar avisos",
            icon = Res.drawable.profile_unfilled,
            onClick = { }
        )

        Spacer(Modifier.height(22.dp))
        SettingsSection("INFORMACIÓN")

        SettingsItem(
            title = "Acerca de TheLöffel",
            subtitle = " ",
            icon = Res.drawable.profile_unfilled,
            onClick = { }
        )

        Spacer(Modifier.height(22.dp))
        HorizontalDivider(color = Color(0xFFE0E0E0))
        Spacer(Modifier.height(12.dp))

        SettingsItem(
            title = "Cerrar sesión",
            icon = Res.drawable.profile_unfilled,
            isDanger = true,
            showArrow = false,
            onClick = {onLogoutClick()}
        )

        SettingsItem(
            title = "Eliminar cuenta",
            icon = Res.drawable.profile_unfilled,
            isDanger = true,
            showArrow = false,
            onClick = { }
        )

        Spacer(Modifier.height(28.dp))

        Text(
            text = "TheLöffel · Versión 1.0",
            color = Color.Gray,
            fontSize = 12.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(24.dp))
    }

}


@Composable
fun SettingsSection(title: String) {
    Text(
        text = title,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold
    )
}


@Composable
private fun SettingsItem(
    title: String,
    icon: DrawableResource,
    onClick: () -> Unit,
    subtitle: String? = null,
    isDanger: Boolean = false,
    showArrow: Boolean = true
) {
    val foreground = if (isDanger) AppColors.settingsRed else Color(0xFF252525)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .background(
                    if (isDanger) AppColors.settingsRedBackground
                    else AppColors.settingsGrayBackground,
                    RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = foreground,
                modifier = Modifier.size(23.dp)
            )
        }

        Spacer(Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = foreground
            )

            subtitle?.let {
                Spacer(Modifier.height(3.dp))
                Text(
                    text = it,
                    fontSize = 12.sp,
                    color = AppColors.settingsGray
                )
            }
        }

        if (showArrow) {
            Icon(
                painter = painterResource(Res.drawable.arrow_right),
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )

        }
    }
}