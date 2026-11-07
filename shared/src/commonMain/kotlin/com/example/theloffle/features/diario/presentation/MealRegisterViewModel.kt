package com.example.theloffle.features.diario.presentation

import LocalStorageManager
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.theloffle.features.recetas.domain.repository.RecipeRepo

class MealRegisterViewModel(
    private val recipeRepo: RecipeRepo,
    private val localStorageManager: LocalStorageManager
) : ViewModel() {

    var uiState by mutableStateOf(MealRegisterUiState())
        private set

    fun onPublishRecipe(imageBytes: ByteArray?) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)

            // 1. Guardar la imagen localmente si existe
            val localImagePath = imageBytes?.let {
                localStorageManager.saveImageToInternalStorage(it)
            }

            // 2. Crear el objeto de dominio
            val newRecipe = Recipe(
                id = generateUniqueId(),
                name = uiState.title,
                ingredients = uiState.ingredients,
                tags = uiState.selectedTags,
                imageUrl = localImagePath,
                published = true
            )

            // 3. Enviar a la capa de datos
            recipeRepo.addRecipe(newRecipe)

            uiState = uiState.copy(isLoading = false, isSuccess = true)
        }
    }
}