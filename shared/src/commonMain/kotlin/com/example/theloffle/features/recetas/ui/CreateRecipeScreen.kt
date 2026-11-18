package com.example.theloffle.features.recetas.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.theloffle.core.UI_System.theme.AppColors
import com.example.theloffle.features.diario.domain.model.FoodPages
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
    val isFormValid = uiState.name.isNotBlank() && uiState.chosenTags.isNotEmpty()

    LaunchedEffect(foodPage) {
        viewModel.initMealType(foodPage)
    }

    RecipeDetailLayout(
        isEditable = true,
        name = uiState.name,
        imageModel = uiState.selectedImageBytes ?: uiState.imageUri,
        author = uiState.author,
        published = false,
        rating = 0.0,
        tags = uiState.chosenTags,
        description = uiState.description,
        ingredients = uiState.ingredients,
        onNameChange = viewModel::onTitleChanged,
        onPickImageClick = { /* TODO: Disparar PhotoPicker */ },
        availableTags = uiState.selectedTags,
        onAddTag = viewModel::onAddTag,
        onRemoveTag = viewModel::onRemoveTag,
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