package com.example.theloffle.features.diario.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.theloffle.core.UI_System.components.Header
import com.example.theloffle.core.UI_System.theme.AppColors
import com.example.theloffle.features.diario.domain.model.FoodPages
import com.example.theloffle.features.diario.presentation.MealRegisterUiState
import com.example.theloffle.features.diario.presentation.MealRegisterViewModel
import com.example.theloffle.features.recetas.domain.model.File
import com.example.theloffle.features.recetas.domain.model.Recipe
import com.example.theloffle.features.recetas.ui.RecipeUiState
import com.example.theloffle.features.recetas.ui.RecipeViewModel

@Composable
fun MealRegisterScreenRoot(
    foodPage: FoodPages,
    viewModel: MealRegisterViewModel,
    recipeViewModel: RecipeViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState
    val recipesUiState by recipeViewModel.uiState.collectAsState()

    LaunchedEffect(foodPage) {
        viewModel.initMealType(foodPage)
    }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            viewModel.resetSuccessState()
            onNavigateBack()
        }
    }

    MealRegisterScreen(
        uiState = uiState,
        recipesUiState = recipesUiState,
        onToggleFilter = recipeViewModel::onToggleFilter,
        onSelectFile = recipeViewModel::onSelectFile,
        onCloseFile = recipeViewModel::onCloseFile,
        onTitleChanged = viewModel::onTitleChanged,
        onDescriptionChanged = viewModel::onDescriptionChanged,
        onAddIngredient = viewModel::onAddIngredient,
        onRemoveIngredient = viewModel::onRemoveIngredient,
        onSaveRecipe = viewModel::onSaveRecipe,
        onCancel = onNavigateBack,
        modifier = modifier
    )
}

@Composable
private fun MealRegisterScreen(
    uiState: MealRegisterUiState,
    recipesUiState: RecipeUiState,
    onToggleFilter: (String) -> Unit,
    onSelectFile: (File) -> Unit,
    onCloseFile: () -> Unit,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onAddIngredient: (String) -> Unit,
    onRemoveIngredient: (String) -> Unit,
    onSaveRecipe: (imageBytes: ByteArray?, isPublished: Boolean) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(1) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Header()

        // Selector de Pestañas
        val tabs = listOf("De mis listas", "Crear receta")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            tabs.forEachIndexed { index, title ->
                val isSelected = selectedTab == index
                if (isSelected) {
                    Button(
                        onClick = { selectedTab = index },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppColors.buttonPrimary,
                            contentColor = AppColors.textPrimary
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(title)
                    }
                } else {
                    OutlinedButton(
                        onClick = { selectedTab = index },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = AppColors.textPrimary
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(title)
                    }
                }
            }
        }

        // Renderizado condicional conectando los parámetros a las sub-secciones
        if (selectedTab == 0) {
            SelectFromListSection(
                uiState = uiState,
                recipesUiState = recipesUiState,
                onToggleFilter = onToggleFilter,
                onSelectFile = onSelectFile,
                onCloseFile = onCloseFile,
                onSaveSelectedRecipe = { recipe ->
                    // Proceso de guardado al elegir una receta de las listas guardadas
                    onSaveRecipe(null, false)
                },
                onCancel = onCancel,
                modifier = Modifier.weight(1f)
            )
        } else {
            CreateRecipeSection(
                uiState = uiState,
                onTitleChanged = onTitleChanged,
                onDescriptionChanged = onDescriptionChanged,
                onAddIngredient = onAddIngredient,
                onRemoveIngredient = onRemoveIngredient,
                onSaveRecipe = onSaveRecipe,
                onCancel = onCancel,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

// -----------------------------------------------------------------------------
// Pestaña 0: Seleccionar de la lista de recetas preexistentes
// -----------------------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SelectFromListSection(
    uiState: MealRegisterUiState,
    recipesUiState: RecipeUiState,
    onToggleFilter: (String) -> Unit,
    onSelectFile: (File) -> Unit,
    onCloseFile: () -> Unit,
    onSaveSelectedRecipe: (Recipe) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    val collections = remember { listOf("Mis Recetas", "Publicadas", "Guardadas") }
    var selectedCollectionIndex by remember { mutableStateOf(0) }
    var selectedRecipe by remember { mutableStateOf<Recipe?>(null) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // 1. Selector de Pestañas (Mis Recetas, Publicadas, Guardadas)
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
                            selectedRecipe = null
                            onCloseFile()
                        }
                    },
                    selectedContentColor = AppColors.textPrimary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    text = { Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold) }
                )
            }
        }

        // 2. Fila de Filtros
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            items(recipesUiState.filters) { filterName ->
                val thisFile = remember(filterName) { File(name = filterName, filter = setOf(filterName)) }
                val isSelected = filterName in recipesUiState.selectedFilters

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

        // 3. Lista o Cuadrícula de Recetas seleccionables
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            when {
                recipesUiState.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                recipesUiState.recipes.isEmpty() -> {
                    Text(
                        text = if (recipesUiState.selectedFilters.isNotEmpty())
                            "No hay recetas guardadas en esta categoría"
                        else
                            "Selecciona una categoría para ver sus recetas",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            items = recipesUiState.recipes,
                            key = { recipe -> recipe.id }
                        ) { recipe ->
                            val isCardSelected = selectedRecipe?.id == recipe.id

                            Card(
                                modifier = Modifier
                                    .height(130.dp)
                                    .clickable { selectedRecipe = recipe },
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isCardSelected) AppColors.buttonPrimary else MaterialTheme.colorScheme.surfaceVariant
                                )
                            ) {
                                Box(modifier = Modifier.fillMaxSize()) {
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
                                                .background(MaterialTheme.colorScheme.primaryContainer),
                                            contentAlignment = Alignment.Center
                                        ) {
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
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier
                                            .align(Alignment.BottomStart)
                                            .padding(8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // 4. Botones de Acción Inferiores
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { selectedRecipe?.let { onSaveSelectedRecipe(it) } },
                enabled = selectedRecipe != null,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.buttonPrimary,
                    contentColor = AppColors.textPrimary
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Añadir a mi registro")
            }

            OutlinedButton(
                onClick = onCancel,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancelar registro")
            }
        }
    }
}

// -----------------------------------------------------------------------------
// Pestaña 1: Crear y registrar una nueva receta
// -----------------------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun CreateRecipeSection(
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
    val chosenFilters = remember { mutableStateListOf<String>() }

    val isFormValid = uiState.name.isNotBlank() && chosenFilters.isNotEmpty()

    var filtersExpanded by remember { mutableStateOf(false) }
    var ingredientsExpanded by remember { mutableStateOf(false) }

    val remainingFilters = remember(uiState.selectedTags, chosenFilters.toList()) {
        uiState.selectedTags.filter { it !in chosenFilters }
    }

    val remainingIngredients = remember(uiState.availableIngredients, uiState.ingredients) {
        uiState.availableIngredients.filter { it !in uiState.ingredients }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (uiState.errorMessage != null) {
                Text(
                    text = uiState.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            OutlinedTextField(
                value = uiState.name,
                onValueChange = onTitleChanged,
                label = { Text("Nombre de la receta *") },
                isError = uiState.name.isBlank(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Foto de la receta",
                style = MaterialTheme.typography.titleMedium,
                color = AppColors.textPrimary
            )
            OutlinedButton(
                onClick = { /* TODO: Disparar PhotoPicker */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (uiState.selectedImageBytes != null) "Cambiar Foto" else "Seleccionar Foto (Opcional)")
            }

            Text(
                text = "Filtros / Categorías *",
                style = MaterialTheme.typography.titleMedium,
                color = AppColors.textPrimary
            )

            ExposedDropdownMenuBox(
                expanded = filtersExpanded,
                onExpandedChange = { filtersExpanded = !filtersExpanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Seleccionar filtro de la lista") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = filtersExpanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = filtersExpanded,
                    onDismissRequest = { filtersExpanded = false }
                ) {
                    if (remainingFilters.isEmpty()) {
                        DropdownMenuItem(
                            text = { Text("No hay más filtros disponibles") },
                            onClick = { filtersExpanded = false }
                        )
                    } else {
                        remainingFilters.forEach { filter ->
                            DropdownMenuItem(
                                text = { Text(filter) },
                                onClick = {
                                    chosenFilters.add(filter)
                                    filtersExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            if (chosenFilters.isEmpty()) {
                Text(
                    text = "Añade al menos una categoría de la lista.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            } else {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    chosenFilters.forEach { filter ->
                        InputChip(
                            selected = false,
                            onClick = { chosenFilters.remove(filter) },
                            label = { Text("$filter  ✕") }
                        )
                    }
                }
            }

            Text(
                text = "Ingredientes",
                style = MaterialTheme.typography.titleMedium,
                color = AppColors.textPrimary
            )

            ExposedDropdownMenuBox(
                expanded = ingredientsExpanded,
                onExpandedChange = { ingredientsExpanded = !ingredientsExpanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Seleccionar ingrediente de la lista") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = ingredientsExpanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = ingredientsExpanded,
                    onDismissRequest = { ingredientsExpanded = false }
                ) {
                    if (remainingIngredients.isEmpty()) {
                        DropdownMenuItem(
                            text = { Text("No hay más ingredientes disponibles") },
                            onClick = { ingredientsExpanded = false }
                        )
                    } else {
                        remainingIngredients.forEach { ingredient ->
                            DropdownMenuItem(
                                text = { Text(ingredient) },
                                onClick = {
                                    onAddIngredient(ingredient)
                                    ingredientsExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            if (uiState.ingredients.isNotEmpty()) {
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

            OutlinedTextField(
                value = uiState.description,
                onValueChange = onDescriptionChanged,
                label = { Text("Descripción (Opcional)") },
                minLines = 3,
                maxLines = 5,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { onSaveRecipe(uiState.selectedImageBytes, false) },
                    enabled = !uiState.isLoading && isFormValid,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(if (uiState.isLoading) "Guardando..." else "Subir Receta")
                }

                Button(
                    onClick = { onSaveRecipe(uiState.selectedImageBytes, true) },
                    enabled = !uiState.isLoading && isFormValid,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(if (uiState.isLoading) "Guardando..." else "Publicar Receta")
                }
            }

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