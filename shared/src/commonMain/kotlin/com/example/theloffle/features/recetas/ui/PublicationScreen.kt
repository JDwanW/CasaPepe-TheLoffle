package com.example.theloffle.features.recetas.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.theloffle.core.UI_System.theme.AppColors
import com.example.theloffle.features.recetas.presentation.PublicationUiState
import com.example.theloffle.features.recetas.presentation.PublicationViewModel
import com.example.theloffle.core.UI_System.components.Header
import com.example.theloffle.features.recetas.ui.components.RecipeDetailLayout

@Composable
fun PublicationScreenRoot(
    recipeId: Int,
    viewModel: PublicationViewModel,
    onNavigateToHome: () -> Unit = {},
    modifier: Modifier = Modifier,
    // Para el boton de atras de escritorio
    showBackButton: Boolean = false,
    onNavigateBack: () -> Unit = {},
) {
    val uiState = viewModel.uiState

    LaunchedEffect(recipeId) {
        viewModel.loadRecipeById(recipeId)
    }

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when {
            uiState.isLoading -> CircularProgressIndicator()
            uiState.errorMessage != null -> Text(
                text = uiState.errorMessage,
                color = MaterialTheme.colorScheme.error
            )
            uiState.recipe != null -> {
                val recipe = uiState.recipe
                RecipeDetailLayout(
                    isEditable = false,
                    name = recipe.name,
                    imageModel = recipe.imageUrl,
                    author = recipe.author,
                    published = recipe.published,
                    rating = recipe.rating ?: 0.0,
                    tags = recipe.tags,
                    description = recipe.description.orEmpty(),
                    ingredients = recipe.ingredients,
                    onHomeClick = onNavigateToHome,
                    // Ponemos visible boton de guardar solo si la receta no es del usuario
                    // que ha iniciado sesion
                    bottomActions = {
                        if (!uiState.isOwnRecipe) {
                            Button(
                                onClick = viewModel::onToggleSaved,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AppColors.buttonPrimary,
                                    contentColor = AppColors.textPrimary
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                // Cambiamos el texto en funcion de si esta guardada la receta o no
                                Text(
                                    if (uiState.isSaved) "Quitar de guardados"
                                    else "Guardar receta"
                                )
                            }
                        }
                        // Boton para volver atras
                        if (showBackButton) {
                            OutlinedButton(
                                onClick = onNavigateBack,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Volver")
                            }
                        }
                    }
                )
            }
        }
    }
}