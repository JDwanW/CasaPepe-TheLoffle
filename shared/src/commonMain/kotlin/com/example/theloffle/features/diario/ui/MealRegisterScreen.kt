package com.example.theloffle.features.diario.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.theloffle.features.diario.domain.model.FoodPages
import com.example.theloffle.features.diario.presentation.MealRegisterUiState
import com.example.theloffle.features.diario.presentation.MealRegisterViewModel

// 1. Contenedor con Estado (Stateful)
@Composable
fun MealRegisterScreenRoot(
    foodPage: FoodPages,
    viewModel: MealRegisterViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState

    // Inicializa el tipo de comida en el estado al entrar
    LaunchedEffect(foodPage) {
        viewModel.initMealType(foodPage)
    }

    // Si guardó con éxito, vuelve atrás
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            viewModel.resetSuccessState()
            onNavigateBack()
        }
    }

    MealRegisterScreen(
        uiState = uiState,
        onTitleChanged = viewModel::onTitleChanged,
        onDescriptionChanged = viewModel::onDescriptionChanged,
        onAddIngredient = viewModel::onAddIngredient,
        onRemoveIngredient = viewModel::onRemoveIngredient,
        onSaveRecipe = viewModel::onSaveRecipe,
        onCancel = onNavigateBack,
        modifier = modifier
    )
}

// 2. Vista Pura (Stateless)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun MealRegisterScreen(
    uiState: MealRegisterUiState,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onAddIngredient: (String) -> Unit,
    onRemoveIngredient: (String) -> Unit,
    onSaveRecipe: (imageBytes: ByteArray?, isPublished: Boolean) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    // Validar requerimientos mínimos: Nombre y al menos 1 ingrediente
    val isFormValid = uiState.name.isNotBlank() && uiState.ingredients.isNotEmpty()

    // Estado local para controlar el desplegable de ingredientes
    var expanded by remember { mutableStateOf(false) }

    // Filtramos la lista de disponibles quitando los que ya fueron añadidos
    val remainingIngredients = remember(uiState.availableIngredients, uiState.ingredients) {
        uiState.availableIngredients.filter { it !in uiState.ingredients }
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Formulario Scrolleable
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Registrar Receta",
                style = MaterialTheme.typography.headlineMedium
            )

            // Mensaje de error si la validación del ViewModel o la API falla
            if (uiState.errorMessage != null) {
                Text(
                    text = uiState.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // 1. Nombre de la receta (Obligatorio)
            OutlinedTextField(
                value = uiState.name,
                onValueChange = onTitleChanged,
                label = { Text("Nombre de la receta *") },
                isError = uiState.name.isBlank(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // 2. Descripción (Opcional)
            OutlinedTextField(
                value = uiState.description,
                onValueChange = onDescriptionChanged,
                label = { Text("Descripción (Opcional)") },
                minLines = 3,
                maxLines = 5,
                modifier = Modifier.fillMaxWidth()
            )

            // 3. Seleccionar Foto (Opcional)
            Text(
                text = "Foto de la receta",
                style = MaterialTheme.typography.titleMedium
            )
            OutlinedButton(
                onClick = { /* TODO: Disparar PhotoPicker */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (uiState.selectedImageBytes != null) "Cambiar Foto" else "Seleccionar Foto (Opcional)")
            }

            // 4. Ingredientes (Obligatorio - Selector desplegable)
            Text(
                text = "Ingredientes *",
                style = MaterialTheme.typography.titleMedium
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Seleccionar ingrediente de la lista") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    if (remainingIngredients.isEmpty()) {
                        DropdownMenuItem(
                            text = { Text("No hay más ingredientes disponibles") },
                            onClick = { expanded = false }
                        )
                    } else {
                        remainingIngredients.forEach { ingredient ->
                            DropdownMenuItem(
                                text = { Text(ingredient) },
                                onClick = {
                                    onAddIngredient(ingredient)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            // Chips con los ingredientes seleccionados (usando texto "✕" para eliminar)
            if (uiState.ingredients.isEmpty()) {
                Text(
                    text = "Añade al menos un ingrediente de la lista.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            } else {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    uiState.ingredients.forEach { ingredient ->
                        InputChip(
                            selected = false,
                            onClick = { onRemoveIngredient(ingredient) },
                            label = { Text("$ingredient  ✕") }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Barra inferior fija con los 3 botones principales
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Subir receta (isPublished = false)
                OutlinedButton(
                    onClick = { onSaveRecipe(uiState.selectedImageBytes, false) },
                    enabled = !uiState.isLoading && isFormValid,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(if (uiState.isLoading) "Guardando..." else "Subir Receta")
                }

                // Publicar receta (isPublished = true)
                Button(
                    onClick = { onSaveRecipe(uiState.selectedImageBytes, true) },
                    enabled = !uiState.isLoading && isFormValid,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(if (uiState.isLoading) "Guardando..." else "Publicar Receta")
                }
            }

            // Cancelar Receta
            OutlinedButton(
                onClick = onCancel,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancelar Receta")
            }
        }
    }
}