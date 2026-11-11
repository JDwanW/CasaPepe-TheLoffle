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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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


@Composable
fun PublicationScreenRoot(
    recipeId: Int,
    viewModel: PublicationViewModel,
    onNavigateToHome: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState

    LaunchedEffect(recipeId) {
        viewModel.loadRecipeById(recipeId)
    }

    PublicationScreen(
        uiState = uiState,
        onNavigateToHome = onNavigateToHome,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PublicationScreen(
    uiState: PublicationUiState,
    onNavigateToHome: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
            }
            uiState.errorMessage != null -> {
                Text(
                    text = uiState.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            uiState.recipe != null -> {
                val recipe = uiState.recipe

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Se le pasa onHomeClick permitiendole el onClick para usar el logo como route a HomeScreen
                    Header(
                        onHomeClick = onNavigateToHome
                    )
                    // Se muestra el nombre de la receta
                    Text(
                        text = recipe.name,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = AppColors.textPrimary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    // En caso de que tenga imagen la receta se muestra
                    // En otro caso se muestra un mensaje de Sin imagen
                    if (!recipe.imageUrl.isNullOrEmpty()) {
                        AsyncImage(
                            model = recipe.imageUrl,
                            contentDescription = recipe.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .width(450.dp)
                                .height(250.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .width(450.dp)
                                .height(250.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.primaryContainer)
                        ){
                            Text(
                                text = "Sin imagen",
                                textAlign = TextAlign.Center,
                                autoSize = TextAutoSize.StepBased(
                                    minFontSize = 10.sp,
                                    maxFontSize = 24.sp
                                )
                            )
                        }
                    }
                    // Se muestra el autor y la valoracion de la receta
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Receta realizada por: " + (recipe.author ?: "Autor desconocido"),
                            style = MaterialTheme.typography.titleMedium,
                            color = AppColors.textPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = if (recipe.published) "★ ${recipe.rating}" else "No publicado",
                            modifier = Modifier
                                .background(
                                    color = AppColors.buttonPrimary,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            color = AppColors.textPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                    // Se muestra la descripcion y la lista de ingredientes de la receta
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(AppColors.buttonPrimary)
                                .padding(12.dp)
                        ) {
                            Column(
                                modifier = Modifier.verticalScroll(rememberScrollState())
                            ) {
                                Text(
                                    text = "Descripción",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = AppColors.textPrimary,
                                    modifier = Modifier.padding(bottom = 6.dp)
                                )
                                Text(
                                    text = recipe.description?.takeIf { it.isNotBlank() }
                                        ?: "Sin descripción disponible.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = AppColors.textPrimary,
                                    lineHeight = 18.sp
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(AppColors.buttonPrimary)
                                .padding(12.dp)
                        ) {
                            Column(
                                modifier = Modifier.verticalScroll(rememberScrollState())
                            ) {
                                Text(
                                    text = "Ingredientes:",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = AppColors.textPrimary,
                                    modifier = Modifier.padding(bottom = 6.dp)
                                )
                                if (recipe.ingredients.isEmpty()) {
                                    Text(
                                        text = "Sin ingredientes especificados.",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = AppColors.textPrimary
                                    )
                                } else {
                                    recipe.ingredients.forEach { ingredient ->
                                        Text(
                                            text = "• $ingredient",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = AppColors.textPrimary,
                                            modifier = Modifier.padding(vertical = 2.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}