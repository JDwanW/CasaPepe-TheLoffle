package com.example.theloffle.features.diario.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.theloffle.core.UI_System.components.Header
import com.example.theloffle.features.diario.domain.model.FoodPages
import com.example.theloffle.features.diario.presentation.components.RegisterBlock
import com.example.theloffle.features.diario.presentation.components.ScrollBlock

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel,
    onMealClick: (FoodPages) -> Unit = {},
    onPublicationClick: (Int) -> Unit = {},                     //Lo mismo que Recipe pero como recipe.publish = true
    onUserClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    HomeScreen(
        uiState = uiState,
        onMealClick = onMealClick,
        onPublicationClick = onPublicationClick,
        onUserClick = onUserClick,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    uiState: HomeUiState,
    onMealClick: (FoodPages) -> Unit,
    onPublicationClick: (Int) -> Unit,
    onUserClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 26.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Header()

                RegisterBlock(
                    registeredMeals = uiState.registeredMeals,
                    onMealClick = onMealClick,
                    modifier = Modifier.height(80.dp)
                )

                ScrollBlock(
                    recipes = uiState.feedRecipes,
                    onPublicationClick = onPublicationClick,
                    onUserClick = onUserClick,
                    modifier = Modifier.fillMaxHeight(1f)
                )
            }
        }
    }
}