package com.example.theloffle.features.configuración.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import theloffle.shared.generated.resources.Res
import theloffle.shared.generated.resources.settings_notifications

@Composable
fun AppearanceDialog(
    isDarkMode: Boolean,
    onDismiss: () -> Unit,
    onThemeChange: (Boolean) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,

        title = {
            Text("Appearance")
        },

        text = {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onThemeChange(false) }
                ) {
                    RadioButton(
                        selected = !isDarkMode,
                        onClick = { onThemeChange(false) }
                    )
                    Text("Light Mode")
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onThemeChange(true) }
                ) {
                    RadioButton(
                        selected = isDarkMode,
                        onClick = { onThemeChange(true) }
                    )
                    Text("Dark Mode")
                }
            }
        },

        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}