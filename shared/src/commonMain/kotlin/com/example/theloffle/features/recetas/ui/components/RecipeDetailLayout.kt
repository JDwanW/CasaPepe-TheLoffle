package com.example.theloffle.features.recetas.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.theloffle.core.UI_System.components.Header
import com.example.theloffle.core.UI_System.theme.AppColors

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun RecipeDetailLayout(
    // boolean para saber si es publicationScreen o createRecipeScreen
    isEditable: Boolean,
    name: String,
    imageModel: Any?, // Puede ser String (imageUrl) o ByteArray (selectedImageBytes)
    author: String?,
    published: Boolean,
    rating: Double,
    tags: List<String>,
    description: String,
    ingredients: List<String>,
    // Callbacks exclusivos para create Recipe
    onNameChange: (String) -> Unit = {},
    onPickImageClick: () -> Unit = {},
    availableTags: List<String> = emptyList(),
    onAddTag: (String) -> Unit = {},
    onRemoveTag: (String) -> Unit = {},
    availableIngredients: List<String> = emptyList(),
    onAddIngredient: (String) -> Unit = {},
    onRemoveIngredient: (String) -> Unit = {},
    onDescriptionChange: (String) -> Unit = {},
    onHomeClick: (() -> Unit)? = null,
    bottomActions: (@Composable () -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var filtersExpanded by remember { mutableStateOf(false) }
    var ingredientsExpanded by remember { mutableStateOf(false) }

    val remainingTags = remember(availableTags, tags) {
        availableTags.filter { it !in tags }
    }
    val remainingIngredients = remember(availableIngredients, ingredients) {
        availableIngredients.filter { it !in ingredients }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Header(onHomeClick = onHomeClick)

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // 1. TÍTULO DE LA RECETA
            if (isEditable) {
                OutlinedTextField(
                    value = name,
                    onValueChange = onNameChange,
                    placeholder = {
                        Text(
                            "Nombre de la receta *",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    },
                    textStyle = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = AppColors.textPrimary
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AppColors.textPrimary,
                        unfocusedBorderColor = AppColors.buttonPrimary
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Text(
                    text = name,
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = AppColors.textPrimary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // 2. IMAGEN DE LA RECETA
            Box(
                modifier = Modifier
                    .width(450.dp)
                    .height(220.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        if (isEditable) AppColors.buttonPrimary.copy(alpha = 0.45f)
                        else MaterialTheme.colorScheme.primaryContainer
                    )
                    .then(if (isEditable) Modifier.clickable { onPickImageClick() } else Modifier),
                contentAlignment = Alignment.Center
            ) {
                if (imageModel != null && imageModel.toString().isNotEmpty()) {
                    AsyncImage(
                        model = imageModel,
                        contentDescription = name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Text(
                        text = if (isEditable) "+ Añadir foto de la receta (Opcional)" else "Sin imagen",
                        color = AppColors.textPrimary,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        autoSize = TextAutoSize.StepBased(minFontSize = 12.sp, maxFontSize = 20.sp),
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            // 3. AUTOR Y ESTADO / VALORACIÓN
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Receta realizada por: ${author?.ifBlank { "Tú" } ?: "Autor desconocido"}",
                    style = MaterialTheme.typography.titleMedium,
                    color = AppColors.textPrimary,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = when {
                        isEditable -> "Borrador"
                        published -> "★ $rating"
                        else -> "No publicado"
                    },
                    modifier = Modifier
                        .background(color = AppColors.buttonPrimary, shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    color = AppColors.textPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }

            // 4. FILTROS / ETIQUETAS
            if (isEditable) {
                ExposedDropdownMenuBox(
                    expanded = filtersExpanded,
                    onExpandedChange = { filtersExpanded = !filtersExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Añadir categoría / filtro *") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = filtersExpanded) },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = filtersExpanded,
                        onDismissRequest = { filtersExpanded = false }
                    ) {
                        if (remainingTags.isEmpty()) {
                            DropdownMenuItem(
                                text = { Text("No hay más filtros disponibles") },
                                onClick = { filtersExpanded = false }
                            )
                        } else {
                            remainingTags.forEach { filter ->
                                DropdownMenuItem(
                                    text = { Text(filter) },
                                    onClick = {
                                        onAddTag(filter)
                                        filtersExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            if (tags.isNotEmpty()) {
                if (isEditable) {
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        tags.forEach { tag ->
                            InputChip(
                                selected = true,
                                onClick = { onRemoveTag(tag) },
                                label = { Text("#$tag  ✕", fontWeight = FontWeight.SemiBold) },
                                colors = InputChipDefaults.inputChipColors(
                                    selectedContainerColor = AppColors.buttonPrimary,
                                    selectedLabelColor = AppColors.textPrimary
                                ),
                                border = null
                            )
                        }
                    }
                } else {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        items(tags) { tag ->
                            Box(
                                modifier = Modifier
                                    .background(color = AppColors.buttonPrimary, shape = RoundedCornerShape(16.dp))
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "#$tag",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = AppColors.textPrimary
                                )
                            }
                        }
                    }
                }
            }

            // 5. TARJETA VERDE DE DESCRIPCIÓN (misma estética que PublicationScreen)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(AppColors.buttonPrimary)
                    .padding(14.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Descripción",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.textPrimary,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                    if (isEditable) {
                        BasicTextField(
                            value = description,
                            onValueChange = onDescriptionChange,
                            textStyle = MaterialTheme.typography.bodyMedium.copy(
                                color = AppColors.textPrimary,
                                lineHeight = 18.sp
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            decorationBox = { innerTextField ->
                                if (description.isEmpty()) {
                                    Text(
                                        text = "Escribe los pasos de preparación (opcional)...",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = AppColors.textPrimary.copy(alpha = 0.6f)
                                    )
                                }
                                innerTextField()
                            }
                        )
                    } else {
                        Text(
                            text = description.takeIf { it.isNotBlank() } ?: "Sin descripción disponible.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = AppColors.textPrimary,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            // 6. TARJETA VERDE DE INGREDIENTES (misma estética que PublicationScreen)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(AppColors.buttonPrimary)
                    .padding(14.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Ingredientes:",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.textPrimary
                    )

                    if (isEditable) {
                        ExposedDropdownMenuBox(
                            expanded = ingredientsExpanded,
                            onExpandedChange = { ingredientsExpanded = !ingredientsExpanded },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = "",
                                onValueChange = {},
                                readOnly = true,
                                placeholder = { Text("Seleccionar ingrediente de la lista") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = ingredientsExpanded) },
                                shape = RoundedCornerShape(10.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedContainerColor = Color.White.copy(alpha = 0.65f),
                                    focusedContainerColor = Color.White.copy(alpha = 0.85f)
                                ),
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
                    }

                    if (ingredients.isEmpty()) {
                        Text(
                            text = if (isEditable) "Añade ingredientes desde el desplegable." else "Sin ingredientes especificados.",
                            style = MaterialTheme.typography.bodySmall,
                            color = AppColors.textPrimary.copy(alpha = 0.75f)
                        )
                    } else {
                        ingredients.forEach { ingredient ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "• $ingredient",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = AppColors.textPrimary,
                                    modifier = Modifier.weight(1f)
                                )
                                if (isEditable) {
                                    Text(
                                        text = "✕",
                                        color = MaterialTheme.colorScheme.error,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .clickable { onRemoveIngredient(ingredient) }
                                            .padding(horizontal = 8.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // 7. BOTONES INFERIORES (solo en CreateRecipeScreen)
        bottomActions?.invoke()
    }
}