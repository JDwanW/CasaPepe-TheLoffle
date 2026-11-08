package com.example.theloffle.features.diario.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.theloffle.features.diario.presentation.MealRegisterUiState
import com.example.theloffle.features.diario.presentation.MealRegisterViewModel
import com.example.theloffle.features.recetas.domain.model.Recipe

// 1. Contenedor con Estado (Stateful)
@Composable
fun MealRegisterScreenRoot(
    viewModel: MealRegisterViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    selectedRecipe: Recipe? = null // Opcional por si se abre para editar una receta existente
) {
    val uiState = viewModel.uiState

    // Efecto secundario de navegación tras guardado exitoso
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            viewModel.resetSuccessState()
            onNavigateBack()
        }
    }

    MealRegisterScreen(
        uiState = uiState,
        selectedRecipe = selectedRecipe,
        onTitleChanged = viewModel::onTitleChanged,
        onAddIngredient = viewModel::onAddIngredient,
        onRemoveIngredient = viewModel::onRemoveIngredient,
        onPublishRecipe = viewModel::onPublishRecipe,
        modifier = modifier
    )
}

// 2. Vista Pura (Stateless, 100% previsualizable y testeable)
@Composable
private fun MealRegisterScreen(
    uiState: MealRegisterUiState,
    selectedRecipe: Recipe?,
    onTitleChanged: (String) -> Unit,
    onAddIngredient: (String) -> Unit,
    onRemoveIngredient: (String) -> Unit,
    onPublishRecipe: (ByteArray?, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // ... Campos de formulario (TextField para título, lista de ingredientes, selector de tags)

        Button(
            onClick = {
                // Ejemplo de llamada para publicar receta
                onPublishRecipe(null, true)
            },
            enabled = !uiState.isLoading
        ) {
            Text(if (uiState.isLoading) "Guardando..." else "Publicar Receta")
        }
    }
}