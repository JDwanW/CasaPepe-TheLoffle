package com.example.theloffle.features.configuración.presentation.ui


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LanguageDialog(
    onDismiss: () -> Unit,
    onLanguageSelected: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,

        title = {
            Text("Seleccionar idioma")
        },

        text = {
            Column {
                val languages = listOf(
                    "es" to "Español",
                    "de" to "Deutsch",
                    "en" to "English",
                    "zh" to "中文（简体）"
                )

                languages.forEach { (code, name) ->
                    Text(
                        text = name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onLanguageSelected(code)
                            }
                            .padding(vertical = 14.dp)
                    )
                }
            }
        },

        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
