package com.example.theloffle.features.recetas.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
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
import androidx.compose.ui.unit.sp
import com.example.theloffle.core.UI_System.components.Header
import com.example.theloffle.features.recetas.domain.model.File
// NEW ADDED FOR IMAGE OF RECIPE
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import coil3.compose.AsyncImage
import com.example.theloffle.core.UI_System.theme.AppColors

@Composable
fun RecipeScreenRoot(
    viewModel: RecipeViewModel,
    onRecipeClick: (Int) -> Unit = {},
    onNavigateToHome: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    RecipeScreen(
        uiState = uiState,
        onToggleFilter = viewModel::onToggleFilter,
        onSelectFile = viewModel::onSelectFile,
        onRecipeClick = onRecipeClick,
        onCloseFile = viewModel::onCloseFile,
        onNavigateToHome = onNavigateToHome,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RecipeScreen(
    uiState: RecipeUiState,
    onToggleFilter: (String) -> Unit,
    onSelectFile: (File) -> Unit,
    onRecipeClick: (Int) -> Unit,
    onCloseFile: () -> Unit,
    onNavigateToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val collections = remember { listOf("Mis Recetas", "Publicadas", "Guardadas") }
    var selectedCollectionIndex by remember { mutableStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Se le pasa onHomeClick permitiendole el onClick para usar el logo como route a HomeScreen
        Header(
            onHomeClick = onNavigateToHome
        )
        /*
        Creating File Row "Button" so user can choose which recipes he wanna search from
        */
        SecondaryTabRow(
            selectedTabIndex = selectedCollectionIndex,
            modifier = Modifier.fillMaxWidth()
        ) {
            collections.forEachIndexed { index, title ->
                Tab(
                    selected = selectedCollectionIndex == index,
                    onClick = {
                        if (selectedCollectionIndex != index) {
                            selectedCollectionIndex = index
                            onCloseFile()
                        }
                    },
                    selectedContentColor = AppColors.textPrimary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    text = { Text(text = title, fontSize = 15.sp, fontWeight = FontWeight.SemiBold) }
                )
            }
        }
        /*
        Creating Filter Row "Button" so user can choose which filter he wanna search from
        */
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            items(uiState.filters) { filterName ->
                val thisFile = remember(filterName) { File(name = filterName, filter = setOf(filterName)) }
                val isSelected = filterName in uiState.selectedFilters

                FilterChip(
                    selected = isSelected,
                    enabled = !isSelected,
                    onClick = {
                        onToggleFilter(filterName)
                        onSelectFile(thisFile)
                    },
                    label = { Text(filterName) }
                )
            }
        }
        /*
        Showing to user the recipes from the file and filter he chose
        */
        Spacer(modifier = Modifier.height(16.dp))
        when {
            /*
            Case: Loading
            */
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            /*
            Case: Empty -> it shows a message if theres no category chose or theres no recipe on that category
            */
            uiState.recipes.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (uiState.selectedFilters.isNotEmpty()) "No hay recetas guardadas en esta categoria"
                                else "Selecciona una categoria para ver sus recetas",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            /*
            Case: Category -> its shows all the recipes on that category
            */
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(
                        items = uiState.recipes,
                        key = { recipe -> recipe.id }
                    ) { recipe ->
                        Card(
                            modifier = Modifier
                                .height(140.dp)
                                .clickable { onRecipeClick(recipe.id) },
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                /*
                                If theres image in the recipe it shows it
                                ioc it shows a black box
                                */
                                if (!recipe.imageUrl.isNullOrEmpty()) {
                                    AsyncImage(
                                         model = recipe.imageUrl,
                                         contentDescription = recipe.name,
                                         contentScale = ContentScale.Crop,
                                         modifier = Modifier.fillMaxSize()
                                    )
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
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
                                /*
                                "Overlay" to show the text
                                */
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            Brush.verticalGradient(
                                                colors = listOf(
                                                    Color.Transparent,
                                                    Color.Black.copy(alpha = 0.75f)
                                                )
                                            )
                                        )
                                )
                                Text(
                                    text = recipe.name,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(10.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}