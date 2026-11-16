package com.example.theloffle.features.diario.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.theloffle.core.UI_System.components.Header
import com.example.theloffle.core.UI_System.theme.AppColors
import com.example.theloffle.features.diario.domain.model.FoodPages
import com.example.theloffle.features.diario.presentation.HomeUiState
import com.example.theloffle.features.diario.presentation.HomeViewModel
import com.example.theloffle.features.diario.presentation.components.RegisterBlock
import com.example.theloffle.features.diario.presentation.components.ScrollBlock

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel,
    onNavigateToSelectRecipe: (FoodPages) -> Unit = {},
    onNavigateToCreateRecipe: (FoodPages) -> Unit = {},
    onPublicationClick: (Int) -> Unit = {},                     //Lo mismo que Recipe pero como recipe.publish = true
    onUserClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    HomeScreen(
        uiState = uiState,
        onNavigateToSelectRecipe = onNavigateToSelectRecipe,
        onNavigateToCreateRecipe = onNavigateToCreateRecipe,
        onPublicationClick = onPublicationClick,
        onUserClick = onUserClick,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    uiState: HomeUiState,
    onNavigateToSelectRecipe: (FoodPages) -> Unit,
    onNavigateToCreateRecipe: (FoodPages) -> Unit,
    onPublicationClick: (Int) -> Unit,
    onUserClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var mealForBottomSheet by remember { mutableStateOf<FoodPages?>(null) }
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 26.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Header()
            RegisterBlock(
                registeredMeals = uiState.registeredMeals,
                onRegisterMealClick = { foodPage -> mealForBottomSheet = foodPage },
                onRegisteredMealClick = { recipeId -> onPublicationClick(recipeId) },
                modifier = Modifier.height(80.dp)
            )
        }
        ScrollBlock(
            recipes = uiState.feedRecipes,
            onPublicationClick = onPublicationClick,
            onUserClick = onUserClick,
            modifier = Modifier.fillMaxWidth().weight(1f)
        )
    }
    mealForBottomSheet?.let { selectedFoodPage ->
        ModalBottomSheet(
            onDismissRequest = { mealForBottomSheet = null },
            containerColor = AppColors.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "¿Cómo quieres registrar tu comida?",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.textPrimary
                )

                Spacer(modifier = Modifier.height(4.dp))

                Button(
                    onClick = {
                        mealForBottomSheet = null
                        onNavigateToSelectRecipe(selectedFoodPage)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.buttonPrimary,
                        contentColor = AppColors.textPrimary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("De mis listas", fontWeight = FontWeight.SemiBold)
                }

                OutlinedButton(
                    onClick = {
                        mealForBottomSheet = null
                        onNavigateToCreateRecipe(selectedFoodPage)
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = AppColors.textPrimary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Crear receta", fontWeight = FontWeight.SemiBold)
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}