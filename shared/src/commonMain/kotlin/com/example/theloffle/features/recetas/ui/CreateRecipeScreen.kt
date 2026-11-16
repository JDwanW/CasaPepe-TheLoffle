package com.example.theloffle.features.recetas.ui

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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.theloffle.core.UI_System.components.Header
import com.example.theloffle.core.UI_System.theme.AppColors
import com.example.theloffle.features.diario.domain.model.FoodPages
import com.example.theloffle.features.diario.presentation.MealRegisterUiState
import com.example.theloffle.features.diario.presentation.MealRegisterViewModel
import com.example.theloffle.features.recetas.ui.components.RecipeDetailLayout

@Composable
fun CreateRecipeScreenRoot(
    foodPage: FoodPages,
    viewModel: MealRegisterViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState
    val chosenFilters = remember { mutableStateListOf<String>() }
    val isFormValid = uiState.name.isNotBlank() && chosenFilters.isNotEmpty()

    LaunchedEffect(foodPage) {
        viewModel.initMealType(foodPage)
    }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            viewModel.resetSuccessState()
            onNavigateBack()
        }
    }

    RecipeDetailLayout(
        isEditable = true,
        name = uiState.name,
        imageModel = uiState.selectedImageBytes ?: uiState.imageUri,
        author = uiState.author,
        published = false,
        rating = 0.0,
        tags = chosenFilters,
        description = uiState.description,
        ingredients = uiState.ingredients,
        onNameChange = viewModel::onTitleChanged,
        onPickImageClick = { /* TODO: Disparar PhotoPicker */ },
        availableTags = uiState.selectedTags,
        onAddTag = { if (it !in chosenFilters) chosenFilters.add(it) },
        onRemoveTag = { chosenFilters.remove(it) },
        availableIngredients = uiState.availableIngredients,
        onAddIngredient = viewModel::onAddIngredient,
        onRemoveIngredient = viewModel::onRemoveIngredient,
        onDescriptionChange = viewModel::onDescriptionChanged,
        bottomActions = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { viewModel.onSaveRecipe(uiState.selectedImageBytes, false) },
                        enabled = !uiState.isLoading && isFormValid,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(if (uiState.isLoading) "Guardando..." else "Subir Receta")
                    }

                    Button(
                        onClick = { viewModel.onSaveRecipe(uiState.selectedImageBytes, true) },
                        enabled = !uiState.isLoading && isFormValid,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppColors.buttonPrimary,
                            contentColor = AppColors.textPrimary
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(if (uiState.isLoading) "Guardando..." else "Publicar Receta")
                    }
                }

                OutlinedButton(
                    onClick = onNavigateBack,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cancelar Receta")
                }
            }
        },
        modifier = modifier
    )
}